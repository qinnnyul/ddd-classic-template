package com.ddd.demo;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = {"com.ddd.demo"},
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class NamingConventionTest {

    @ArchTest
    public static final ArchRule Controller_must_endwith_controller_in_application_layer = classes()
            .that()
            .resideInAnyPackage("com.ddd.demo.api")
            .should()
            .haveNameMatching(".*Controller")
            .as("Restful API的类应该以Controller结尾");

    @ArchTest
    public static final ArchRule Dto_must_endwith_request_in_application_layer = classes()
            .that()
            .resideInAnyPackage("com.ddd.demo.api.dto").should().haveNameMatching(".*Request.*")
            .orShould().haveNameMatching(".*Response.*")
            .as("DTO的类应该以Request or Response结尾");


    @ArchTest
    public static final ArchRule ApplicationService_must_endwith_AppService_in_application_layer = classes()
            .that()
            .resideInAnyPackage("com.ddd.demo.application")
            .should()
            .haveNameMatching(".*Service")
            .as("应用服务的类应该以Service结尾");

    @ArchTest
    public static final ArchRule Dao_must_endwith_dao_in_infrastructure_layer = classes()
            .that()
            .resideInAnyPackage("com.ddd.demo.infrastructure.dao")
            .should()
            .haveNameMatching(".*Dao")
            .as("基础设施层的DAO类应该以Dao结尾");


}
