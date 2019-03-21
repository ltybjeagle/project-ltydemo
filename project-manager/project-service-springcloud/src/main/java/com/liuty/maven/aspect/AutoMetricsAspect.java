package com.liuty.maven.aspect;

/**
 * 系统性能监控
@Aspect
@Component
 */
public class AutoMetricsAspect {
/*
    private static final Logger logger = LoggerFactory.getLogger(AutoMetricsAspect.class);

    protected ConcurrentMap<String, Meter> meters = new ConcurrentHashMap<>();
    protected ConcurrentMap<String, Meter> exceptionMeters = new ConcurrentHashMap<>();
    protected ConcurrentMap<String, Timer> timers = new ConcurrentHashMap<>();
    protected ConcurrentMap<String, Counter> counters = new ConcurrentHashMap<>();

    @Autowired
    public MetricRegistry metricRegistry;*/

    /**
     * 拦截此包下的所有请求
     */
    /*
    @Pointcut("execution(* com.liuty.maven.service.*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods() && @annotation(countedAnnotation)")
    public void instrumentCounted(JoinPoint joinPoint, Counted countedAnnotation) {
        String name = name(joinPoint.getTarget().getClass(), StringUtils
                .hasLength(countedAnnotation.name()) ? countedAnnotation.name()
                : joinPoint.getSignature().getName(), "counter");
        Counter counter = counters.computeIfAbsent(name, key -> metricRegistry.counter(key));
        counter.inc();
    }

    @Before("serviceMethods() && @annotation(meteredAnnotation)")
    public void instrumentMetered(JoinPoint joinPoint, Metered meteredAnnotation) {
        String name = name(joinPoint.getTarget().getClass(), StringUtils
                .hasLength(meteredAnnotation.name()) ? meteredAnnotation.name()
                : joinPoint.getSignature().getName(), "meter");
        Meter meter = meters.computeIfAbsent(name, key -> metricRegistry.meter(key));
        meter.mark();
    }

    @AfterThrowing(pointcut = "serviceMethods() && @annotation(exMeteredAnnotation)", throwing = "ex")
    public void instrumentExceptionMetered(JoinPoint joinPoint, Throwable ex
            , ExceptionMetered exMeteredAnnotation) {
        String name = name(joinPoint.getTarget().getClass(), StringUtils
                .hasLength(exMeteredAnnotation.name()) ? exMeteredAnnotation.name()
                : joinPoint.getSignature().getName(), "meter", "exception");
        Meter meter = exceptionMeters.computeIfAbsent(name, key -> metricRegistry.meter(key));
        meter.mark();
    }

    @Around("serviceMethods() && @annotation(timedAnnotation)")
    public Object instrumentTimed(ProceedingJoinPoint proceedingJoinPoint, Timed timedAnnotation)
            throws Throwable {
        String name = name(proceedingJoinPoint.getTarget().getClass(), StringUtils
                .hasLength(timedAnnotation.name()) ? timedAnnotation.name()
                : proceedingJoinPoint.getSignature().getName(), "timer");
        Timer timer = timers.computeIfAbsent(name, key -> metricRegistry.timer(key));
        Timer.Context tc = timer.time();
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            tc.stop();
            logger.info("timer : {}, {}, {}, {}", timer.getCount(), timer.getOneMinuteRate()
                    , timer.getFiveMinuteRate(), timer.getFifteenMinuteRate());
        }
    }
    */
}
