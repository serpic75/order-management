package com.aspect.queue.aspect;

import com.aspect.queue.repo.ClassifiedIdentifierRepo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

import static java.lang.String.format;

//@Aspect
//@Component
public class SequenceAspect {

	private static final Logger log = LoggerFactory.getLogger(SequenceAspect.class);

	private final ClassifiedIdentifierRepo classifiedIdentifierRepo;

	public SequenceAspect(ClassifiedIdentifierRepo classifiedIdentifierRepo) {
		this.classifiedIdentifierRepo = classifiedIdentifierRepo;
	}

	@Pointcut("within(@com.aspect.queue.model.annotations.SequenceBean *)")
	public void getIncrementedSequence() {
	}

	@Pointcut("execution(* *.*(..))")
	protected void allMethod() {
	}

	@Before("getIncrementedSequence() && allMethod() ")
	public void countAccessBefore(JoinPoint joinPoint, HttpServletRequest request) {
		try {
			classifiedIdentifierRepo.findBy();
//			classifiedIdentifierRepo.save(new ClassifiedIdentifierEntity());
		} catch (Exception e) {
			log.error(format("error increasing number of access request: %s, error message is: %s",
					joinPoint.getSignature().getName(), e.getMessage()));
		}
	}

}
