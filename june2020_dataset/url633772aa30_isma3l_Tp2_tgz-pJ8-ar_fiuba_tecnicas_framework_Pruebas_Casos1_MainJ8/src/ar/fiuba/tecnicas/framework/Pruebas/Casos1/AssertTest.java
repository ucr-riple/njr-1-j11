package ar.fiuba.tecnicas.framework.Pruebas.Casos1;

import ar.fiuba.tecnicas.framework.JTest.*;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
/*
Responsabilidad: Controlar una serie de test creados por el usuario
 */
public class AssertTest implements TestCreator {

    public void objectComparisonEqualTest() {
        int k=2;
        int h=2;
        Assert.assertEquals("Objects have different value", k, h);
    }
    public void assertArrayEqualsTest() {
        Double[] list1 = {1.2, 9.8, 4.6};
        Double[] list2 = {1.2, 9.8, 4.6};
        Assert.assertArrayEquals("Arrays have different value",list1,list2);
    }

    public void conditionComparisonTrueTest() {
        boolean trueCondition= (2<3);
        Assert.assertTrue("The condition is false",trueCondition);
    }

    public void conditionComparisonFalseTest() {
        boolean falseCondition= (2<3);
        Assert.assertFalse("The condition is true",falseCondition);
    }

    public void referenceComparisonSameTest() {
        int num1 = 2;
        int num2 = num1;
        Assert.assertSame("Objects have different reference",num1,num2);
    }
    public void doubleComparisonEquals(){
        double num1=2.32456;
        double num2=2.32466;
        double delta=0.01;
        Assert.assertEquals("2.32456 is not equal to 2.32466 considering delta 0.01",num1,num2,delta);
    }
    public void referenceComparisonNotSameTest() {
        int num1 = 2;
        int num2 = 6;
        Assert.assertNotSame("num1 and num2 have the same reference",num1,num2);
    }

    public void nullObjectComparisonTest() {
        String chain=null;
        Assert.assertNull("Chain is not null",chain);
    }
    public void notNullObjectComparisonTest() {
        String chain= "abc";
        Assert.assertNotNull("Chain is null",chain);
    }

    public void assertThatTest() {
        int[] actualArray={1,2,3,4};
        Assert.assertThat("ActualArray size is not 4",actualArray.length, is(4));
    }


    @Override
    public Test getTest()throws Exception {
        TestSuite test = new TestSuite("AssertSuite");

        test.addTest(new TestCase("objectComparisonEqualTest", Arrays.asList("SLOW","INTERNET")) {
            @Override
            public void runTest() {
                objectComparisonEqualTest();
            }
        });
        test.addTest(new TestCase("assertArrayEqualsTest") {
            @Override
            public void runTest() {
                assertArrayEqualsTest();
            }
        });
        test.addTest(new TestCase("conditionComparisonTrueTest",Arrays.asList("INTERNET")) {
            @Override
            public void runTest(){
                conditionComparisonTrueTest();
            }
        });
        test.addTest(new TestCase("conditionComparisonFalseTest") {
            @Override
            public void runTest(){
                conditionComparisonFalseTest();
            }
        });
        test.addTest(new TestCase("referenceComparisonSameTest") {
            @Override
            public void runTest(){
                referenceComparisonSameTest();
            }
        });
        test.addTest(new TestCase("doubleComparisonEquals") {
            @Override
            public void runTest(){
                doubleComparisonEquals();
            }
        });
        test.addTest(new TestCase("referenceComparisonNotSameTest") {
            @Override
            public void runTest(){
                referenceComparisonNotSameTest();
            }
        });
        test.addTest(new TestCase("nullObjectComparisonTest") {
            @Override
            public void runTest(){
                nullObjectComparisonTest();
            }
        });
        test.addTest(new TestCase("notNullObjectComparisonTest") {
            @Override
            public void runTest(){
                notNullObjectComparisonTest();
            }
        });
        test.addTest(new TestCase("assertThatTest") {
            @Override
            public void runTest(){
                assertThatTest();
            }
        });
        return test;
    }
}
