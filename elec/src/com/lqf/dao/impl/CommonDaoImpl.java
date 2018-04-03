package com.lqf.dao.impl;

import com.lqf.dao.ICommonDao;
import com.lqf.util.GenericTypeUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CommonDaoImpl<T>  extends HibernateDaoSupport implements ICommonDao<T> {

    Class entityClass= GenericTypeUtils.getGenericSuperClass(this.getClass());


    @Resource(name = "sessionFactory")
    public final void setSessionFactoryDi(SessionFactory sessionFactory){
        this.setSessionFactory(sessionFactory);
    }




    @Override
    public void save(T entity) {

        this.getHibernateTemplate().save(entity);
    }

    @Override
    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
    }

    //使用主键ID查询对象
    @Override
    public T findObjectByID(Serializable id) {
      return  (T) this.getHibernateTemplate().get(entityClass,id);
    }

    //使用1个主键和多个ID的数组
    @Override
    public void deleteBojectByIDs(Serializable... ids) {
        if (ids!=null && ids.length>0){
            for (Serializable id:ids){
                Object entity=this.findObjectByID(id);
                this.getHibernateTemplate().delete(entity);
            }
        }
    }

    /**
     * 将对象封装成集合，使用集合删除集合中存放的所有对象
     * 将数据查询获取封装到List，先查询在删除
     * @param list
     */
    @Override
    public void deleteObjectByCollection(List<T> list) {

        this.getHibernateTemplate().deleteAll(list);
    }

    @Override
    public List<T> findCollectionByConditionNoPage(String condition, Object[] params, Map<String, String> orderby) {

        String hql="select o from "+entityClass.getSimpleName()+" o where 1=1";
        //解析map集合，获取排序的语句
        String orderbyHql=this.orderby(orderby);
        final String finalHql=hql+condition+orderbyHql;
        //方式一

        //方式二
        List<T> list=this.getHibernateTemplate().execute(new HibernateCallback<List<T>>() {

            @Override
            public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query=session.createQuery(finalHql);
                if (params!=null && params.length>0){
                    for (int i=0;i<params.length;i++){
                        query.setParameter(i,params[i]);
                    }
                }
                return query.list();
            }
        });
        return list;

    }

    private String orderby(Map<String, String> orderby) {

        StringBuffer buffer=new StringBuffer();
        if (orderby!=null && orderby.size()>0){
            buffer.append(" order by");
            for (Map.Entry<String,String> map:orderby.entrySet()){
                buffer.append(map.getKey()+" "+map.getValue()+",");
            }
            //删除最后一个逗号
            buffer.deleteCharAt(buffer.length()-1);
        }

        return buffer.toString();
    }
}
