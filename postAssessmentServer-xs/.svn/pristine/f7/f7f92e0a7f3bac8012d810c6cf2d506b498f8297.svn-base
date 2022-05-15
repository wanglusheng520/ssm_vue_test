package com.beneway.common.common.utils.log;


import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.system.agency.AgencyDao;
import com.beneway.common.dao.system.dict.DictDao;
import org.apache.http.client.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CompareObjectUtils{
    private static CompareObjectUtils compareObjectUtils;
    private DictDao dictDao;
    private AgencyDao agencyDao;

    public CompareObjectUtils(DictDao dictDao, AgencyDao agencyDao) {
        this.dictDao = dictDao;
        this.agencyDao = agencyDao;
    }


    @PostConstruct
    public void init() {
        compareObjectUtils = this;

    }
    /**
     * 获取两个对象同名属性内容不相同的列表
     * @param oldObject 对象1
     * @param newObject 对象2
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public static List<Map<String, Object>> compareTwoClass(Object oldObject, Object newObject) throws IllegalAccessException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        //获取对象的class
        Class<?> oldClass = oldObject.getClass();
        Class<?> newClass = newObject.getClass();
        //获取对象的属性列表
        Field[] oldField = oldClass.getDeclaredFields();
        Field[] newField = newClass.getDeclaredFields();
        //遍历属性列表oldField
        for (int i = 0; i < oldField.length; i++) {
            if(oldField[i].isAnnotationPresent(FieldMeta.class))
                //遍历属性列表newField
                for (int j = 0; j < newField.length; j++) {
                    //如果oldField[i]属性名与newField[j]属性名内容相同
                    if (oldField[i].getName().equals(newField[j].getName())) {
                        oldField[i].setAccessible(true);
                        newField[j].setAccessible(true);
                        //如果oldField[i]属性值与newField[j]属性值内容不相同
                        if (!compareTwo(oldField[i].get(oldObject), newField[j].get(newObject)) && oldField[i].isAnnotationPresent(FieldMeta.class) && newField[j].isAnnotationPresent(FieldMeta.class)) {
                            FieldMeta metaAnnotation = oldField[i].getAnnotation(FieldMeta.class);
                            Map<String, Object> map2 = new HashMap<String, Object>();
                            map2.put("name", metaAnnotation.name());
                            map2.put("old", oldField[i].get(oldObject) == null ? "" : oldField[i].get(oldObject) );
                            map2.put("new", newField[j].get(newObject));
                            //解决时间格式化问题-bean上加了@DateTimeFormat(pattern="yyyy-MM-dd")
                            if(oldField[i].isAnnotationPresent(DateTimeFormat.class) && newField[j].isAnnotationPresent(DateTimeFormat.class) ){
                                String old = DateUtils.formatDate((Date) oldField[i].get(oldObject),oldField[i].getAnnotation(DateTimeFormat.class).pattern());
                                map2.put("old",old == null ? "": old);
                                map2.put("new", DateUtils.formatDate((Date) newField[j].get(newObject),newField[j].getAnnotation(DateTimeFormat.class).pattern()));
                            }
                            //解决数据字典text/value转换问题-bean上加了@Dict(dicCode = "groupField",isCommon = false)
                            if(oldField[i].isAnnotationPresent(Dict.class) && newField[j].isAnnotationPresent(Dict.class)){
                                boolean isCommon = oldField[i].getAnnotation(Dict.class).isCommon();
                                String dicCode = oldField[i].getAnnotation(Dict.class).dicCode();
                                if("dict".equals(dicCode)){
                                    map2.put("old",compareObjectUtils.dictDao.findByNeValue(oldField[i].getAnnotation(Dict.class).neKey(),oldField[i].get(oldObject).toString()));
                                    map2.put("new",compareObjectUtils.dictDao.findByNeValue(newField[i].getAnnotation(Dict.class).neKey(),newField[i].get(newObject).toString()));
                                }else if("agency".equals(dicCode)){
                                    map2.put("old",compareObjectUtils.agencyDao.selectById(oldField[i].get(oldObject).toString()).getAgencyName());
                                    map2.put("new",compareObjectUtils.agencyDao.selectById(newField[i].get(oldObject).toString()).getAgencyName());
                                }
                            }
                            list.add(map2);
                        }
                        break;
                    }
                }
        }
        return list;
    }

    //对比两个数据是否内容相同
    public static boolean compareTwo(Object object1, Object object2) {

        if (object1 == null && object2 == null) {
            return true;
        }
        // 因源数据是没有进行赋值，是null值，改为""。
        //if (object1 == "" && object2 == null) {
        //    return true;
        //}
        //if (object1 == null && object2 == "") {
        //    return true;
        // }
        if (object1 == null && object2 != null) {
            return false;
        }

        if (object1.equals(object2)) {
            return true;
        }
        return false;
    }

}
