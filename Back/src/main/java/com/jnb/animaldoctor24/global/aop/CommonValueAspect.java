//package com.jnb.animaldoctor24.global.aop;
//
//import com.jnb.animaldoctor24.global.common.CommonData;
//import com.jnb.animaldoctor24.global.common.CommonDataHolder;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//
//import java.util.Date;
//
//public class CommonValueAspect {
//
//    private static final String SYSTEM_USER_ID = "system";
//
//    @Pointcut("execution(* com.jnb.animaldoctor24.domain.**.mapper.*Mapper.merge*(..))")
//    public void mergeQuery() {
//    }
//
//    @Pointcut("execution(* com.jnb.animaldoctor24.domain.**.mapper.*Mapper.insert*(..))")
//    public void insertQuery() {
//    }
//
//    @Pointcut("execution(* com.jnb.animaldoctor24.domain.**.mapper.*Mapper.update*(..))")
//    public void updateQuery() {
//    }
//
//    @Pointcut("execution(* com.jnb.animaldoctor24.domain.**.mapper.*Mapper.delete*(..))")
//    public void deleteQuery() {
//    }
//
//    @Before("mergeQuery() || insertQuery() || updateQuery() || deleteQuery()")
//    public void assignInfo(JoinPoint jp) {
//        CommonData commonData = CommonDataHolder.getCommonData();
//        Date date = new Date();
//        Object[] objs = jp.getArgs();
//        for (Object o : objs) {
//            if (o instanceof BaseDto) {
//                BaseDto base = (BaseDto) o;
//                if (base.getCreateDate() == null) {
//                    base.setCreateDate(date);
//                }
//                if (base.getModifyDate() == null) {
//                    base.setModifyDate(date);
//                }
//                if (base.getCreateBy() == null) {
//                    base.setCreateBy(commonData == null || commonData.getMemberId() == null ? SYSTEM_USER_ID : commonData.getMemberId());
//                }
//                if (base.getModifyBy() == null) {
//                    base.setModifyBy(commonData == null || commonData.getMemberId() == null ? SYSTEM_USER_ID : commonData.getMemberId());
//                }
//            }
//        }
//
//    }
//}
