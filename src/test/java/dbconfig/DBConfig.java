package dbconfig;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * mysql
     * config
     */
    @Test
    public void testConfig1() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
        logger.info("configuration: {}", configuration);
        ProcessEngine processEngine = configuration.buildProcessEngine();
        logger.info("processEngine: {}", processEngine.getName());
        processEngine.close();
    }

    /**
     * druid config
     */
    @Test
    public void testConfigByDruid() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti_druid.cfg.xml");
        logger.info("configuration: {}", configuration);
        ProcessEngine processEngine = configuration.buildProcessEngine();
        logger.info("processEngine: {}", processEngine.getName());
        processEngine.close();
    }

}
