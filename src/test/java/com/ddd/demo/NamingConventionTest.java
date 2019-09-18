package com.ddd.demo;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = {"com.nbcb.billservice.bill", "com.nbcb.billservice.command"},
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class NamingConventionTest {

    @ArchTest
    public static final ArchRule Controller_must_endwith_api_in_application_layer = classes()
            .that()
            .resideInAnyPackage("com.nbcb.billservice.command.application.api", "com.nbcb.billservice.bill.application.api")
            .should()
            .haveNameMatching(".*Api")
            .as("Restful API的类应该以Api结尾");


    @ArchTest
    public static final ArchRule ApplicationService_must_endwith_AppService_in_application_layer = classes()
            .that()
            .resideInAnyPackage("com.nbcb.billservice.command.application.service", "com.nbcb.billservice.bill.application.service")
            .should()
            .haveNameMatching(".*AppService")
            .as("应用服务的类应该以AppService结尾");

}
