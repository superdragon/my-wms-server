package com.jiaming.wms.account.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.jiaming.wms.account.bean.entity.Account;
import com.jiaming.wms.account.bean.vo.*;
import com.jiaming.wms.account.service.IAccountService;
import com.jiaming.wms.captcha.bean.entity.Captcha;
import com.jiaming.wms.captcha.service.ICaptchaService;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.common.exception.ApiException;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.permission.bean.entity.AccountRole;
import com.jiaming.wms.permission.bean.vo.PermissionDataVO;
import com.jiaming.wms.permission.service.IPermissionService;
import com.jiaming.wms.utils.JwtInfo;
import com.jiaming.wms.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dragon
 */
@Api(tags = "账户API")
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    IAccountService accountService;

    @Autowired
    ICaptchaService captchaService;

    @Autowired
    IPermissionService permissionService;

    @ApiOperation("账号密码登录")
    @RequestMapping("/login")
    public ResultVO<LoginDataVO> login(@RequestParam("name") String loginName,
                                       @RequestParam("password") String loginPwd,
                                       @RequestParam String captchaCode,
                                       @RequestParam String captchaKey) {

        // 验证请求参数的有效性
        if (StrUtil.isEmpty(loginName) || StrUtil.isEmpty(loginPwd)
                || StrUtil.isEmpty(captchaCode) || StrUtil.isEmpty(captchaKey) ) {
            return new ResultVO<>(ResultCodeEnum.FAIL_PARAM_MISSING);
        }
        // 验证输入的验证码是否正确
        // 通过key获取图片验证码正确的内容
        Captcha captcha = captchaService.getForCacheById(captchaKey);

        if (StrUtil.isEmpty(captcha.getCode())) {
            throw new ApiException("验证码已过期");
        }
        // 判断输入的验证码和真实的内容是否一致
        if (!captchaCode.equals(captcha.getCode())) {
            throw new ApiException("验证码不正确");
        }

        // 获取处理结果
        Account account = accountService.login(loginName, SecureUtil.md5(loginPwd));
        if (account == null) {
            throw new ApiException("账户或密码不正确");
        }

        // 判断账户是否可用
        if (account.getStatus() == 1) {
            throw new ApiException("账户已被禁用");
        }

        // 获取账户相关角色信息
        List<AccountRole> roles = permissionService.getRolesByAccountId(account.getId());
        if (roles.size() <= 0) {
            return new ResultVO<>(ResultCodeEnum.INVALID_PERMISSIONS);
        }

        // 获取账户角色相关权限信息
        PermissionDataVO permissionDataVO = permissionService.getPermissionsByRoleId(roles);

        // 封装响应结果
        LoginDataVO loginDataVO = new LoginDataVO();
        BeanUtils.copyProperties(account, loginDataVO);
        // 生成token
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId(account.getId());
        jwtInfo.setUserName(account.getUserName());
        jwtInfo.setLoginName(account.getLoginName());
        String token = JwtUtil.createSign(jwtInfo);
        loginDataVO.setToken(token);
        loginDataVO.setPermission(permissionDataVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, loginDataVO);
    }

    @ApiOperation("基于token的登录")
    @PostMapping("/autoLogin")
    public ResultVO<LoginDataVO> autoLogin(@RequestParam String token) {
        // 验证token
        if (StrUtil.isEmpty(token)) {
            return new ResultVO<>(ResultCodeEnum.INVALID_TOKEN);
        }

        try {
            JwtInfo secretInfo = JwtUtil.getSecretInfo(token);

            long accountId = secretInfo.getId();
            Account account = accountService.getById(accountId);
            if (account == null) {
                throw new ApiException("账户不存在");
            }

            // 判断账户是否可用
            if (account.getStatus() == 1) {
                throw new ApiException("账户已被禁用");
            }

            // 获取账户相关角色信息
            List<AccountRole> roles = permissionService.getRolesByAccountId(account.getId());
            if (roles.size() <= 0) {
                return new ResultVO<>(ResultCodeEnum.INVALID_PERMISSIONS);
            }

            // 获取账户角色相关权限信息
            PermissionDataVO permissionDataVO = permissionService.getPermissionsByRoleId(roles);

            // 封装响应结果
            LoginDataVO loginDataVO = new LoginDataVO();
            BeanUtils.copyProperties(account, loginDataVO);
            // 生成token
            JwtInfo jwtInfo = new JwtInfo();
            jwtInfo.setId(account.getId());
            jwtInfo.setUserName(account.getUserName());
            jwtInfo.setLoginName(account.getLoginName());
            String newToken = JwtUtil.createSign(jwtInfo);
            loginDataVO.setToken(newToken);
            loginDataVO.setPermission(permissionDataVO);
            return new ResultVO<>(ResultCodeEnum.SUCCESS, loginDataVO);
        } catch (Exception e) {
            return new ResultVO<>(ResultCodeEnum.INVALID_PERMISSIONS);
        }
    }

    @ApiOperation("获取账户信息")
    @RequestMapping("/get")
    public ResultVO<Account> get(Long id) {
        Account account = accountService.getById(id);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, account);
    }

    @ApiOperation("更新账户基本信息")
    @PostMapping("/update")
    public ResultVO update(@RequestBody @Valid UpdateAccountVO accountVO) {
        Account account = accountService.getByLoginName(accountVO);
        if (account != null) {
            throw new ApiException("账户已存在");
        }
        // 2、验证手机号在数据库中是否已经存在
        Account ac = accountService.getByPhone(accountVO);
        if (ac != null) {
            throw new ApiException("手机号已存在");
        }

        Account newAccount = new Account();
        BeanUtils.copyProperties(accountVO, newAccount);
        accountService.updateById(newAccount);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("更新密码")
    @PostMapping("/updatePwd")
    public ResultVO updatePwd(@RequestBody @Valid UpdatePwdVO updatePwdVO) {
        // 确认旧密码是否正确
        Account account = accountService.getById(updatePwdVO.getId());
        if (!account.getLoginPwd().equals(SecureUtil.md5(updatePwdVO.getLoginPwd()))) {
            throw new ApiException("旧密码不正确");
        }
        // 新密码和确认密码是否一致
        if (!updatePwdVO.getNewLoginPwd().equals(updatePwdVO.getCheckNewLoginPwd())) {
            throw new ApiException("新密码与确认密码不一致");
        }
        // 确认旧密码和新密码是否一致
        if (updatePwdVO.getLoginPwd().equals(updatePwdVO.getNewLoginPwd())) {
            throw new ApiException("旧密码和新密码不能一样");
        }
        // 更新密码
        Account newAccount = new Account();
        newAccount.setId(updatePwdVO.getId());
        newAccount.setLoginPwd(SecureUtil.md5(updatePwdVO.getNewLoginPwd()));
        accountService.updateById(newAccount);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("更新密码")
    @PostMapping("/updateNewPwd")
    public ResultVO updateNewPwd(@RequestBody @Valid UpdateNewPwdVO updatePwdVO) {
        // 新密码和确认密码是否一致
        if (!updatePwdVO.getNewLoginPwd().equals(updatePwdVO.getCheckNewLoginPwd())) {
            return new ResultVO<>(ResultCodeEnum.BIZ_FAIL, "新密码与确认密码不一致");
        }
        // 更新密码
        Account newAccount = new Account();
        newAccount.setId(updatePwdVO.getId());
        newAccount.setLoginPwd(SecureUtil.md5(updatePwdVO.getNewLoginPwd()));
        accountService.updateById(newAccount);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("添加账户")
    @PostMapping("/save")
    public ResultVO save(@RequestBody @Valid SaveAccountVO saveAccountVO) {
        // 验证必填项
        String loginName = saveAccountVO.getLoginName();
        String loginPwd = saveAccountVO.getLoginPwd();
        String checkLoginPwd = saveAccountVO.getCheckLoginPwd();
        String mobile = saveAccountVO.getMobile();

        // 验证密码和确认密码是否一致
        if (!loginPwd.equals(checkLoginPwd)) {
            throw new ApiException("密码和确认密码不一致");
        }
        // 验证账户或手机号在数据库中是否已经存在
        List<Account> accounts = accountService.getByLoginNameOrPhone(loginName, mobile);
        if (accounts.size() > 0) {
            throw new ApiException("账户或手机号已存在");
        }

        Account account = new Account();
        BeanUtils.copyProperties(saveAccountVO, account);
        // 对密码进行加密保存
        account.setLoginPwd(SecureUtil.md5(account.getLoginPwd()));
        accountService.save(account);
        return new ResultVO(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("分页查询账户信息")
    @PostMapping("/find")
    public ResultVO<MyPage<Account>> find(@RequestBody @Valid FindAccountVO findAccountVO) {
        MyPage<Account> list = accountService.find(findAccountVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, list);
    }

    // ids=1,2,3,4,5
    @ApiOperation("批量删除账户")
    @PostMapping("/remove")
    public ResultVO remove(@RequestParam("ids") String ids) {
        String[] temp = ids.split(",");
        List<Long> idList = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            String str = temp[i];
            idList.add(Long.parseLong(str));
        }
        accountService.batchDel(idList);
        return new ResultVO(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("导出账户信息")
    @GetMapping("/exportCsv")
    public ResponseEntity<byte[]> exportCsv(String keyWord) {

        List<Account> accounts = accountService.find(keyWord);

        // 写入 csv
        StringBuilder sb = new StringBuilder();
        // 定义 csv 标题栏内容，注意最后一列内容要换行
        sb.append("编号,账号,名称,手机号,更新时间,创建时间\n");
        for (Account account : accounts) {
            sb.append(account.getId() +  ",");
            sb.append(account.getLoginName() + ",");
            sb.append(account.getUserName() + ",");
            sb.append(account.getMobile() + ",");
            sb.append(DateUtil.format(account.getUpdateTime(), "yyyy-MM-dd HH:mm:ss") + ",");
            sb.append(DateUtil.format(account.getCreateTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
        }

        try {
            // 定义导出文件的名称
            String downloadFileName = "账号信息.csv";
            // 设置响应头部信息
            HttpHeaders headers = new HttpHeaders();
            // 设置编码  为了解决中文名称乱码问题
            downloadFileName = new String(downloadFileName.getBytes("UTF-8"), "iso-8859-1");
            // 将文件表述信息添加到 http 头信息中
            headers.setContentDispositionFormData("attachment", downloadFileName);
            // 在 http 头添加以下信息，浏览器会自动弹出下载提示框
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // 创建响应实体对象
            // FileUtils.readFileToByteArray(file) 读取文件写到字节数组中
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(sb.toString().getBytes(), headers, HttpStatus.CREATED);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("导入账户信息")
    @PostMapping("/uploadCsv")
    public ResultVO uploadCsv(@RequestPart("file") MultipartFile file) {
        CsvReader reader = CsvUtil.getReader();
        List<Account> accounts = new ArrayList<>();
        try {
            //从文件中读取 CSV 数据
            // csv文件是什么编码就转换成对应的编码，避免读取流中的中文乱码
            // 例如：上传的csv文件是UTF-8，那么就转换成UTF-8
            CsvData csvData = reader.read(IoUtil.getReader(file.getInputStream(), "UTF-8"));
            // 获取 csv 数据的行记录，并封装到集合中
            List<CsvRow> rows = csvData.getRows();
            // 遍历行
            // 不读取标题行的内容
            for (int i = 1; i < rows.size(); i++) {
                // 获取第 i 行记录
                CsvRow csvRow = rows.get(i);
                // 获取当前行中每个单元格的内容
                List<String> columns = csvRow.getRawList();
                Account account = new Account();
                // 获取姓名
                String name = columns.get(0);
                if (StrUtil.isNotEmpty(name)) {
                    account.setLoginName(name);
                }
                // 获取昵称
                String userName = columns.get(1);
                if (StrUtil.isNotEmpty(userName)) {
                    account.setUserName(userName);
                }
                // 获取手机号
                String mobile = columns.get(3);
                if (StrUtil.isNotEmpty(mobile)) {
                    account.setMobile(mobile);
                }
                accounts.add(account);
            }
            // 批量保存业务
            accountService.saveBatch(accounts);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取文件失败");
        }
        return new ResultVO(ResultCodeEnum.SUCCESS);
    }
}
