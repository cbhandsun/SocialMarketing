package com.socialmarketing.core.dao.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.socialmarketing.core.exception.ApplicationException;


/**
 * Quartz定时程序API操作辅助工具类。
 * 
 * @author Danne Leung
 * 
 */
public final class SchedulerUtil implements InitializingBean,
		MessageSourceAware {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean = null;

	private Scheduler scheduler;

	private MessageSource messageSource;

	public void addJob(JobDetail jobDetail, boolean flag)
			throws SchedulerException {
		scheduler.addJob(jobDetail, flag);
	}

	public void afterPropertiesSet() throws Exception {
		if (schedulerFactoryBean != null) {
			scheduler = (Scheduler) schedulerFactoryBean.getObject();
		} else {
			log.warn(messageSource.getMessage(
					"quartz.exception.schedulerNotStated", null, null));
		}
	}

	private void checkScheduler() {
		if (scheduler == null) {
			throw new ApplicationException("AW0001"); //$NON-NLS-1$
		}
	}

	public boolean deleteJob(String jobName, String groupName) {
		return false;
	}

	public JobDetail getJob(String jobName, String jobGroup)
			throws SchedulerException {
		try {
			return scheduler.getJobDetail(jobName, jobGroup);
		} catch (SchedulerException e) {
			log
					.error(
							messageSource
									.getMessage(
											"quartz.exception.noJobFound", new Object[] { jobName }, null), e); //$NON-NLS-1$
			throw e;
		}
	}

	public Scheduler getScheduler() {
		checkScheduler();
		return scheduler;
	}

	public Trigger getTrigger(String triggerName, String groupName)
			throws SchedulerException {
		checkScheduler();
		try {
			return scheduler.getTrigger(triggerName, groupName);
		} catch (SchedulerException e) {
			log
					.error(
							messageSource
									.getMessage(
											"quartz.exception.noJobFound", new Object[] { triggerName }, null), e); //$NON-NLS-1$
			throw e;
		}
	}

	public int getTriggerState(String triggerName, String groupName)
			throws SchedulerException {
		checkScheduler();
		return scheduler.getTriggerState(triggerName, groupName);

	}

	public String getTriggerStateName(String triggerName, String groupName)
			throws SchedulerException {
		checkScheduler();
		String triggerState = messageSource.getMessage(
				"quartz.tigger.status.none", null, null); //$NON-NLS-1$
		int state = scheduler.getTriggerState(triggerName, groupName);
		switch (state) {
		case Trigger.STATE_BLOCKED:
			triggerState = messageSource.getMessage(
					"quartz.tigger.status.blocked", null, null); //$NON-NLS-1$
			break;
		case Trigger.STATE_COMPLETE:
			triggerState = messageSource.getMessage(
					"quartz.tigger.status.complete", null, null); //$NON-NLS-1$
			break;
		case Trigger.STATE_ERROR:
			triggerState = messageSource.getMessage(
					"quartz.tigger.status.error", null, null);; //$NON-NLS-1$
			break;
		case Trigger.STATE_NORMAL:
			triggerState = messageSource.getMessage(
					"quartz.tigger.status.normal", null, null); //$NON-NLS-1$
			break;
		case Trigger.STATE_PAUSED:
			triggerState = messageSource.getMessage(
					"quartz.tigger.status.paused", null, null); //$NON-NLS-1$
			break;
		default:
		}
		return triggerState;
	}

	public void pauseTrigger(String triggerName, String groupName)
			throws SchedulerException {
		checkScheduler();
		try {
			scheduler.pauseTrigger(triggerName, groupName);
		} catch (SchedulerException e) {
			log
					.error(
							messageSource
									.getMessage(
											"quartz.exception.cantStopTrigger", new Object[] { triggerName }, null), e); //$NON-NLS-1$
			throw e;
		}

	}

	public void rescheduleTrigger(String triggerName, String groupName,
			Trigger newTrigger) throws SchedulerException {
		checkScheduler();
		try {
			scheduler.rescheduleJob(triggerName, groupName, newTrigger);
		} catch (SchedulerException e) {
			log
					.error(
							messageSource
									.getMessage(
											"quartz.exception.cantStartTrigger", new Object[] { triggerName }, null), e); //$NON-NLS-1$
			throw e;
		}
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void triggerJob(String jobName, String groupName)
			throws SchedulerException {
		try {
			scheduler.triggerJob(jobName, groupName);
		} catch (SchedulerException e) {
			log
					.error(
							messageSource
									.getMessage(
											"quartz.exception.cantStartTrigger", new Object[] { jobName }, null), e); //$NON-NLS-1$
			throw e;
		}
	}
}
