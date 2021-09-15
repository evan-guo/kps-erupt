package xyz.erupt.upms.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;
import xyz.erupt.upms.enums.MenuStatus;
import xyz.erupt.upms.model.EruptMenu;
import xyz.erupt.upms.model.EruptRole;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.vo.EruptMenuVo;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author YuePeng
 * date 2020-04-13
 */
@Service
public class EruptMenuService implements DataProxy<EruptMenu> {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private EruptUserService eruptUserService;

    @Resource
    private EruptContextService eruptContextService;


    public List<EruptMenuVo> geneMenuListVo(List<EruptMenu> menus) {
        List<EruptMenuVo> list = new ArrayList<>();
        menus.stream().filter(menu -> menu.getStatus() == MenuStatus.OPEN.getValue()).forEach(menu -> {
            Long pid = null == menu.getParentMenu() ? null : menu.getParentMenu().getId();
            list.add(new EruptMenuVo(menu.getId(), menu.getCode(), menu.getName(), menu.getType(), menu.getValue(), menu.getIcon(), pid));
        });
        return list;
    }

    public List<EruptMenu> getUserAllMenu(EruptUser eruptUser) {
        List<EruptMenu> menus;
        if (null != eruptUser.getIsAdmin() && eruptUser.getIsAdmin()) {
            menus = entityManager.createQuery("from EruptMenu order by sort", EruptMenu.class).getResultList();
        } else {
            Set<EruptMenu> menuSet = new HashSet<>();
            eruptUser.getRoles().stream().filter(EruptRole::getStatus).map(EruptRole::getMenus).forEach(menuSet::addAll);
            menus = menuSet.stream().sorted(Comparator.comparing(EruptMenu::getSort, Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
        }
        return menus;
    }

    @Override
    public void addBehavior(EruptMenu eruptMenu) {
        Integer obj = (Integer) entityManager
                .createQuery("select max(sort) from " + EruptMenu.class.getSimpleName())
                .getSingleResult();
        Optional.ofNullable(obj).ifPresent(it -> eruptMenu.setSort(it + 10));
        eruptMenu.setStatus(MenuStatus.OPEN.getValue());
    }

    @Override
    public void afterAdd(EruptMenu eruptMenu) {
        if (eruptMenu.getCode().contains("/")) {
            throw new EruptWebApiRuntimeException("菜单编码禁止出现 '/' 字符");
        }
        if (StringUtils.isNotBlank(eruptMenu.getType()) && StringUtils.isBlank(eruptMenu.getValue())) {
            throw new EruptWebApiRuntimeException("选择菜单类型时，类型值不能为空");
        }
        eruptUserService.cacheUserInfo(eruptUserService.getCurrentEruptUser(), eruptContextService.getCurrentToken());
    }

    @Override
    public void afterUpdate(EruptMenu eruptMenu) {
        this.afterAdd(eruptMenu);
    }


    @Override
    public void afterDelete(EruptMenu eruptMenu) {
        this.afterAdd(eruptMenu);
    }

}
