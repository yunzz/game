import com.google.common.base.Charsets

def PATTERN = "%d{yyyy-MM-dd HH:mm:ss.SSS}-[%35.35F:%4.4line] [%25.25thread] %-5level [%25.25logger{35}] - %msg %n"
def PATTERN_GAME = "[access]%d{yyyy-MM-dd HH:mm:ss.SSS}-[%35.35F:%4.4line] [%25.25thread] %-5level [%25.25logger{35}] - %msg %n%rEx"
def PATTERN_TRACE = "%d{yyyy-MM-dd HH:mm:ss.SSS}-[%20.20thread] - %msg %n%rEx"
def PATTERN_KLOG = "%d{yyyy-MM-dd HH:mm:ss.SSS} - %msg %n%rEx"

def appenderList = ["server", "error"]
def console = true
def klogDir = System.getProperty("klog")
def logBaseDir = System.getProperty("baseLogDir")
def loglvl = System.getProperty("log.lvl")
def loglvl0 = DEBUG
if (loglvl != null) {

    loglvl0 = valueOf(loglvl)
}
if (klogDir == null) {
    klogDir = "logs/"
}
if (logBaseDir == null) {
    logBaseDir = "logs/"
}
if (console) {
    appenderList.add("stdout")
    appender("stdout", ConsoleAppender) {
        target = "System.out"
        encoder(PatternLayoutEncoder) {
            pattern = PATTERN
            charset = Charsets.UTF_8
        }
    }
}


appender("access", RollingFileAppender) {
    file = "$logBaseDir/access.log"
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN_GAME
        charset = Charsets.UTF_8
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "$logBaseDir/access_%d{yyyy-MM-dd}.log"
    }
}

appender("cast", RollingFileAppender) {
    file = "$logBaseDir/cast.log"
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN_GAME
        charset = Charsets.UTF_8
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "$logBaseDir/cast_%d{yyyy-MM-dd}.log"
    }
}
appender("server", RollingFileAppender) {
    file = "$logBaseDir/server.log"
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN
        charset = Charsets.UTF_8
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "$logBaseDir/server_%d{yyyy-MM-dd}.log"
//        maxHistory = 3
    }
}

appender("error", RollingFileAppender) {
    file = "$logBaseDir/error.log"
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN
        charset = Charsets.UTF_8
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "$logBaseDir/error_%d{yyyy-MM-dd}.log"
    }
    filter(ch.qos.logback.classic.filter.ThresholdFilter) {
        level = WARN
    }
}

appender("module", RollingFileAppender) {
    file = "$logBaseDir/module.log"
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN
        charset = Charsets.UTF_8
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "$logBaseDir/module_%d{yyyy-MM-dd}.log"
    }
    filter(ch.qos.logback.classic.filter.ThresholdFilter) {
        level = INFO
    }
}
appender("val", RollingFileAppender) {
    file = "$logBaseDir/val.log"
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN_KLOG
        charset = Charsets.UTF_8
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "$logBaseDir/val_%d{yyyy-MM-dd}.log"
    }
    filter(ch.qos.logback.classic.filter.ThresholdFilter) {
        level = INFO
    }
}

logger("access", DEBUG, ["access"])
logger("common", DEBUG, ["access"])
logger("cast", DEBUG, ["cast"])
logger("module", INFO, ["module"])
logger("val", INFO, ["val"])

root(loglvl0, appenderList)

scan("30 seconds")
jmxConfigurator()