package com.licc.dove.security.service;

import com.licc.dove.security.domain.DovePlat;
import com.licc.dove.security.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @author lichangcchao
 * @version 1.0.0
 * @date 2018/1/16 13:43
 * @see
 */
@Service
public class PlatService extends BaseServiceImpl<DovePlat> {

  public void update(DovePlat plat){
   this.commonDao.update(plat);
  }

}
