package ar.fiuba.tecnicas.framework.Pruebas.Casos1;


import ar.fiuba.tecnicas.framework.JTest.*;

public class SuiteATest implements TestCreator {
    private TestSuite suite;
    public SuiteATest() {
        this.suite=new TestSuite("SuiteATest"){

            @Override
            public void setUp() {
                getContext().put("k",2);
                getContext().put("h",2);
                getContext().put("list1",new Double[]{1.2, 9.8, 4.6});
                getContext().put("list2",new Double[]{1.2, 9.8, 4.6});
                getContext().put("trueCondition",2<3);
            }

        };
    }
    public void assetEqualWithSetUpTest() {

        Assert.assertEquals("Objects have different value", suite.getContext().get("k"), suite.getContext().get("h"));
    }
    public void assertArrayEqualsWithSetUpTest() {
        Assert.assertArrayEquals("Arrays have different value",(Double[])suite.getContext().get("list1"),(Double[])suite.getContext().get("list2"));
    }
    public void assertTrueWithSetUpTest() {
        Assert.assertTrue("The condition is false",(Boolean)suite.getContext().get("trueCondition"));
    }

    @Override
    public Test getTest()throws Exception {
        suite.addTest(new TestCase("assertArrayEqualsWithSetUpTest") {
            @Override
            public void runTest() {
                assertArrayEqualsWithSetUpTest();
            }
        });
        suite.addTest(new TestCase("assetEqualWithSetUpTest") {
            @Override
            public void runTest() {
                assetEqualWithSetUpTest();
            }
        });
        suite.addTest(new TestCase("assertTrueWithSetUpTest") {
            @Override
            public void runTest(){
                assertTrueWithSetUpTest();
            }
        });
        return suite;
    }
}
