//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.model;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.upms.model.EruptUserVo;
import xyz.erupt.upms.service.EruptUserService;

@Component
public class BiDataProxy implements DataProxy<Bi> {
    @Resource
    private EruptUserService eruptUserService;
    @PersistenceContext
    private EntityManager entityManager;

    public BiDataProxy() {
    }

    @Transactional
    public void beforeUpdate(Bi bi) {
        this.entityManager.clear();
        Bi bbi = (Bi)this.entityManager.find(Bi.class, bi.getId());
        if (null != bi.getBiDimension()) {
            Iterator var3 = bi.getBiDimension().iterator();

            while(var3.hasNext()) {
                BiDimension dimension = (BiDimension)var3.next();
                dimension.setBi(bi);
            }
        }

        if (StringUtils.isNotBlank(bi.getSqlStatement()) && StringUtils.isNotBlank(bbi.getSqlStatement()) && !bi.getSqlStatement().equals(bbi.getSqlStatement())) {
            BiHistory bh = new BiHistory();
            bh.setBi(bi);
            bh.setSqlStatement(bbi.getSqlStatement());
            bh.setOperateTime(new Date());
            bh.setOperateUser(new EruptUserVo(this.eruptUserService.getCurrentUid()));
            this.entityManager.persist(bh);
            this.entityManager.flush();
        }

    }

    public void afterFetch(Collection<Map<String, Object>> list) {
        Iterator var2 = list.iterator();

        while(var2.hasNext()) {
            Map<String, Object> map = (Map)var2.next();
            map.put("view", "#/fill/bi/" + map.get("code"));
        }

    }
}
