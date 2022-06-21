package com.jiaming.wms.account.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.account.bean.entity.Account;
import com.jiaming.wms.account.bean.vo.FindAccountVO;
import com.jiaming.wms.account.bean.vo.UpdateAccountVO;
import com.jiaming.wms.account.mapper.AccountMapper;
import com.jiaming.wms.account.service.IAccountService;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.permission.bean.entity.AccountRole;
import com.jiaming.wms.permission.service.IAccountRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dragon
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    IAccountRoleService accountRoleService;

    @Override
    public Account login(String loginName, String loginPwd) {
        QueryWrapper<Account> queryWrapper = Wrappers.query();
        queryWrapper.eq("login_name", loginName).eq("login_pwd", loginPwd).last("LIMIT 1");
        return this.getOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDel(List<Long> idList) {
        // 删除账户
        this.removeByIds(idList);
        // 删除账户与角色的关系
        QueryWrapper<AccountRole> queryWrapper = Wrappers.query();
        queryWrapper.in("account_id", idList);
        accountRoleService.remove(queryWrapper);
    }

    @Override
    public Account getByLoginName(UpdateAccountVO account) {
        /*
         * SELECT id, phone, login_name,
         *         login_pwd, nick_name,
         *         gender, update_time, create_time
         *         FROM account
         *         WHERE login_name = #{loginName} AND id != #{id}
         */
        // 因为账户必须唯一，在更新账户名称时，如果账户名已经被其他用户注册过，那么不能更新成功。
        QueryWrapper<Account> queryWrapper = Wrappers.query();
        queryWrapper.eq("login_name", account.getLoginName())
                .ne("id", account.getId()).last("LIMIT 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public Account getByPhone(UpdateAccountVO accountVO) {
        /*
        SELECT id, phone, login_name,
        login_pwd, nick_name,
        gender, update_time, create_time
        FROM account
        WHERE phone = #{phone} AND id != #{id}
         */
        QueryWrapper<Account> queryWrapper = Wrappers.query();
        queryWrapper.eq("mobile", accountVO.getMobile())
                .ne("id", accountVO.getId()).last("LIMIT 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public List<Account> getByLoginNameOrPhone(String loginName, String phone) {
        /*
        SELECT id, mobile, login_name,
        login_pwd, nick_name,
        update_time, create_time
        FROM account
        WHERE phone = #{phone} OR login_name = #{loginName}
         */
        QueryWrapper<Account> queryWrapper = Wrappers.query();
        queryWrapper.eq("mobile", phone)
                .or().eq("login_name", loginName);
        return this.list(queryWrapper);
    }

    @Override
    public MyPage<Account> find(FindAccountVO findAccountVO) {
        // 创建MP的分页对象
        IPage<Account> page = new Page<>();
        page.setSize(findAccountVO.getPageSize());
        page.setCurrent(findAccountVO.getPageNum());

        // 创建查询条件
        /*
        SELECT id, phone, login_name, image_url,
               nick_name, gender, update_time, create_time
        FROM account
        WHERE 1 = 1
        <if test="vo.keyWord != null and vo.keyWord != ''">
            AND login_name LIKE CONCAT('%', #{vo.keyWord}, '%') OR phone LIKE CONCAT('%', #{vo.keyWord}, '%')
        </if>
         */
        QueryWrapper<Account> queryWrapper = Wrappers.query();
        String keyWord = findAccountVO.getKeyWord();
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("login_name", keyWord)
                    .or().like("mobile", keyWord);
        }

        // 执行分页
        this.page(page, queryWrapper);

        // 封装返回的分页对象
        MyPage<Account> myPage = new MyPage<>();
        myPage.setItems(page.getRecords());
        myPage.setPageNum(page.getCurrent());
        myPage.setPageSize(page.getSize());
        myPage.setTotalNum(page.getTotal());
        myPage.setTotalPage(page.getPages());

        return myPage;
    }

    @Override
    public List<Account> find(String keyWord) {
        QueryWrapper<Account> queryWrapper = Wrappers.query();
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("login_name", keyWord)
                    .or().like("mobile", keyWord);
        }
        List<Account> accounts = this.list(queryWrapper);
        for (Account account : accounts) {
            // 脱敏处理
            account.setMobile(DesensitizedUtil.mobilePhone(account.getMobile()));
        }
        return accounts;
    }
}
