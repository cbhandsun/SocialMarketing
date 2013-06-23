/**
 * 
 */
package com.socialmarketing.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.core.services.impl.BaseService;
import com.socialmarketing.model.NumResource;
import com.socialmarketing.util.DateUtil;

/**
 * @author hongtao
 *
 */
@Service(value = "numResourceService")
public class NumResourceServiceImpl extends BaseService<NumResource> implements NumResourceService {
	   private static final Logger logger =
			   LoggerFactory.getLogger(NumResourceServiceImpl.class.getName());
	private IDao<NumResource> numResourceDao = null;
	@Autowired
	@Qualifier("numResourceDao")
	@Override
	public void setBaseDao(IDao<NumResource> baseDao) {
		numResourceDao = baseDao;
	}

	/*
	 * 取得当前序列号值
	 * 
	 * @see
	 * solution.auto.imes.service.TmSysNumResourceService#getNextSequence(java
	 * .lang .String )
	 */
	
	/* (non-Javadoc)
	 * @see com.socialmarketing.service.NumResourceService#getNextSequence(java.lang.String)
	 */
	@Override
	public Long getNextSequence(String name) {
		long currentSeq = 1;
		int step = 1;
		long nextSeq = currentSeq + step;
		// find and lock instance.
		NumResource numResource = numResourceDao.findByField(
				NumResource.FIELD_NUMRESOURCE, name, LockMode.PESSIMISTIC_WRITE);
		if (numResource != null) {
			Long minVal = numResource.getMinVal();
			// default min value to 1.
			if (minVal == null) {
				minVal = 1L;
				numResource.setMinVal(minVal);
			}

			// default max value to Long.MAX_VALUE.
			Long maxVal = numResource.getMaxVal();
			if (maxVal == null) {
				maxVal = Long.MAX_VALUE;
				numResource.setMaxVal(maxVal);
			}

			currentSeq = numResource.getNextNo() == null ? minVal : numResource
					.getNextNo();
			step = numResource.getStep() == null ? 1 : numResource.getStep();

			if (step < 1) {
				step = 1;
			}

			if (currentSeq < minVal) {
				currentSeq = minVal;
			}

			Date resetDate = numResource.getResetDate();
			Date currentDate = DateUtil.getCurrentDate();
			if (RESET_TYPE_DAILY.equals(numResource.getResetType())) {
				// 每日进行序列重置操作
				if (resetDate == null) {
					resetDate = currentDate;
					numResource.setResetDate(resetDate);
				}
				String currentDateAsString = DateUtil.format(currentDate,
						DateUtil.FORMAT_DATE_DEFAULT);
				String resetDateAsString = DateUtil.format(resetDate,
						DateUtil.FORMAT_DATE_DEFAULT);
				// 比较日期，按日进行reset
				if (currentDateAsString.compareTo(resetDateAsString) > 0) {
					// reset to minVal.
					currentSeq = minVal;
					// 刷新reset日期
					numResource.setResetDate(currentDate);
				}
			}
			nextSeq = currentSeq + step;

			// increment.
			if (nextSeq > maxVal) {
				logger.warn("Number resource: " + name
						+ " exceed max value, cycle to min value.");
				nextSeq = minVal;
			}
			// update next
			numResource.setStep(step);
			numResource.setNextNo(nextSeq);
			numResourceDao.update(numResource);
		} else {
			logger.warn("Number resource: " + name
					+ " is not be set, create default.");
			numResource = new NumResource();
			numResource.setNumResource(name);
			// INSERT NUM SOURCE
			numResource.setMinVal(1L);
			numResource.setMaxVal(Long.MAX_VALUE);
			numResource.setNextNo(nextSeq);
			numResource.setStep(step);
			numResourceDao.save(numResource);
		}
		return currentSeq;
	}

	/*
	 * 取得无前缀的当前序列号字符串
	 * 
	
	/* (non-Javadoc)
	 * @see com.socialmarketing.service.NumResourceService#getNextSequence(java.lang.String, int)
	 */
	@Override
	public String getNextSequence(String name, int paddingLeftLength) {
		Long sequence = getNextSequence(name);

		String dstNo = String.valueOf(sequence);
		return StringUtils.leftPad(dstNo, paddingLeftLength, '0');
	}
}
