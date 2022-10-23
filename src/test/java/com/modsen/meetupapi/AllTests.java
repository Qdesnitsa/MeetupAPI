package com.modsen.meetupapi;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;


//@Execution(ExecutionMode.CONCURRENT)
//@SelectPackages({"com.modsen.meetupapi.controller",
//        "com.modsen.meetupapi.dao"})
//@Suite
//@IncludeTags({"controller","dao"})
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
//public class AllTests {
//}
