package org.example.uzgotuje;


import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages("org.example.uzgotuje")
@SuiteDisplayName("Uzgotuje tests")
@ExcludeTags("slow")
public class AllTests {
}
