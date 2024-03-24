package kr.itycoon.plutoid.global.aop;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import kr.itycoon.plutoid.biz.common.model.BaseDto;
import kr.itycoon.plutoid.global.common.CommonData;
import kr.itycoon.plutoid.global.common.CommonDataHolder;

@Aspect
@Component
@Slf4j
public class CommonValueAspect {

    private static final String SYSTEM_USER_ID = "system";

    @Pointcut("execution(* kr.itycoon.plutoid.biz.**.mapper.*Mapper.merge*(..))")
    public void mergeQuery() {
    }

    @Pointcut("execution(* kr.itycoon.plutoid.biz.**.mapper.*Mapper.insert*(..))")
    public void insertQuery() {
    }

    @Pointcut("execution(* kr.itycoon.plutoid.biz.**.mapper.*Mapper.update*(..))")
    public void updateQuery() {
    }

    @Pointcut("execution(* kr.itycoon.plutoid.biz.**.mapper.*Mapper.delete*(..))")
    public void deleteQuery() {
    }

    @Before("mergeQuery() || insertQuery() || updateQuery() || deleteQuery()")
    public void assignInfo(JoinPoint jp) {
        CommonData commonData = CommonDataHolder.getCommonData();
        Date date = new Date();
        Object[] objs = jp.getArgs();
        for (Object o : objs) {
            if (o instanceof BaseDto) {
                BaseDto base = (BaseDto) o;
                if (base.getCreateDate() == null) {
                    base.setCreateDate(date);
                }
                if (base.getModifyDate() == null) {
                    base.setModifyDate(date);
                }
                if (base.getCreateBy() == null) {
                    base.setCreateBy(commonData == null || commonData.getMemberId() == null ? SYSTEM_USER_ID : commonData.getMemberId());
                }
                if (base.getModifyBy() == null) {
                    base.setModifyBy(commonData == null || commonData.getMemberId() == null ? SYSTEM_USER_ID : commonData.getMemberId());
                }
            }
        }

    }
}
