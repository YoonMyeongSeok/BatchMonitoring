@ECHO OFF
REM ---------- InterFace Settings ----------
REM ex)JavaBatchJob.bat 배치번호
set LANG=JAVA
set NLS_LANG=_.UTF8
set MX_CHARSET=UTF8

set REPOSITORYPATH=C:\Users\help\.m2\repository
set CLASSPATH=C:\workspace\boneis\target\classes;%REPOSITORYPATH%\org\springframework\spring-context\3.1.1.RELEASE\spring-context-3.1.1.RELEASE.jar;%REPOSITORYPATH%\org\springframework\spring-core\3.1.1.RELEASE\spring-core-3.1.1.RELEASE.jar;%REPOSITORYPATH%\org\springframework\spring-beans\3.1.1.RELEASE\spring-beans-3.1.1.RELEASE.jar;%REPOSITORYPATH%\commons-logging\commons-logging\1.1\commons-logging-1.1.jar;%REPOSITORYPATH%\org\springframework\spring-asm\3.1.1.RELEASE\spring-asm-3.1.1.RELEASE.jar;%REPOSITORYPATH%\org\springframework\spring-expression\3.1.1.RELEASE\spring-expression-3.1.1.RELEASE.jar;%REPOSITORYPATH%\org\springframework\spring-orm\3.1.1.RELEASE\spring-orm-3.1.1.RELEASE.jar;%REPOSITORYPATH%\org\apache\ibatis\ibatis-sqlmap\2.3.4.726\ibatis-sqlmap-2.3.4.726.jar;%REPOSITORYPATH%\commons-dbcp\commons-dbcp\1.2.2\commons-dbcp-1.2.2.jar;%REPOSITORYPATH%\commons-pool\commons-pool\1.3\commons-pool-1.3.jar;%REPOSITORYPATH%\org\springframework\spring-jdbc\3.1.1.RELEASE\spring-jdbc-3.1.1.RELEASE.jar;%REPOSITORYPATH%\org\springframework\spring-tx\3.1.1.RELEASE\spring-tx-3.1.1.RELEASE.jar;%REPOSITORYPATH%\mysql\mysql-connector-java\5.1.9\mysql-connector-java-5.1.9.jar;%REPOSITORYPATH%\org\springframework\spring-context-support\3.1.1.RELEASE\spring-context-support-3.1.1.RELEASE.jar;%REPOSITORYPATH%\org\quartz-scheduler\quartz\1.7.3\quartz-1.7.3.jar;%REPOSITORYPATH%\org\slf4j\slf4j-log4j12\1.6.6\slf4j-log4j12-1.6.6.jar;%REPOSITORYPATH%\org\slf4j\slf4j-api\1.6.6\slf4j-api-1.6.6.jar;%REPOSITORYPATH%\log4j\log4j\1.2.15\log4j-1.2.15.jar;%REPOSITORYPATH%\commons-configuration\commons-configuration\1.9\commons-configuration-1.9.jar;%REPOSITORYPATH%\commons-lang\commons-lang\2.4\commons-lang-2.4.jar

set JAVA_HOME="C:\Program Files\Java\jdk1.6.0_45\bin"
REM ---------- InterFace Settings ----------
%JAVA_HOME%\java com.boneis.support.batchlauncher.BatchJobLauncher %1