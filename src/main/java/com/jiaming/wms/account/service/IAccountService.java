package com.jiaming.wms.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.account.bean.entity.Account;
import com.jiaming.wms.account.bean.vo.FindAccountVO;
import com.jiaming.wms.account.bean.vo.UpdateAccountVO;
import com.jiaming.wms.common.entity.MyPage;

import java.util.List;

/**
 * @author dragon
 */
public interface IAccountService extends IService<Account> {
    Account getByLoginName(UpdateAccountVO account);

    Account getByPhone(UpdateAccountVO accountVO);

    List<Account> getByLoginNameOrPhone(String loginName, String phone);

    MyPage<Account> find(FindAccountVO findAccountVO);

    List<Account> find(String keyWord);

    Account login(String loginName, String loginPwd);

    void batchDel(List<Long> idList);
}
