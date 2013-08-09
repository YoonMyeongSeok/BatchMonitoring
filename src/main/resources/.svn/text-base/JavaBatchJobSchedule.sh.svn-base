#/bin/bash
echo [classpath]${CLASSPATH}

JARPATH=/apps/web/htdocs/servlet/batch.boneis.com/batch/WEB-INF/lib
CLASSPATH=${CLASSPATH}:${JARPATH}/spring-context-3.1.1.RELEASE.jar:${JARPATH}/spring-core-3.1.1.RELEASE.jar:${JARPATH}/spring-beans-3.1.1.RELEASE.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/commons-logging-1.1.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/spring-asm-3.1.1.RELEASE.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/spring-expression-3.1.1.RELEASE.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/spring-orm-3.1.1.RELEASE.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/ibatis-sqlmap-2.3.4.726.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/commons-dbcp-1.2.2.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/commons-pool-1.3.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/spring-jdbc-3.1.1.RELEASE.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/spring-tx-3.1.1.RELEASE.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/mysql-connector-java-5.1.9.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/spring-context-support-3.1.1.RELEASE.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/quartz-1.7.3.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/slf4j-log4j12-1.6.6.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/slf4j-api-1.6.6.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/log4j-1.2.15.jar
CLASSPATH=${CLASSPATH}:${JARPATH}/commons-io-2.1.jar
CLASSPATH=${CLASSPATH}:/usr/lib/jvm/java-1.6.0/lib/tools.jar
CLASSPATH=${CLASSPATH}:/usr/lib/jvm/java-1.6.0/jre/lib/ext
CLASSPATH=${CLASSPATH}:/apps/web/was/tomcat-batch/lib/servlet-api.jar
CLASSPATH=${CLASSPATH}:/apps/web/was/tomcat-batch/lib/jsp-api.jar
CLASSPATH=${CLASSPATH}:/apps/web/htdocs/servlet/batch.boneis.com/batch/WEB-INF/classes
echo [classpath]${CLASSPATH}
echo
echo

#java -cp ${CLASSPATH} com.boneis.support.batchlauncher.BatchJobLauncher $1
java -cp ${CLASSPATH} com.boneis.support.batchlauncher.BatchJobScheduler
