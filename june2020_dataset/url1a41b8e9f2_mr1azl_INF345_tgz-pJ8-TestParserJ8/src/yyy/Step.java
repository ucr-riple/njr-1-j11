/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package yyy;

import xx.Compiler;
import org.w3c.dom.Element;

/**
 * @author Dmitri Plotnikov
 * @version $Revision: 652845 $ $Date: 2008-05-02 12:46:46 -0500 (Fri, 02 May 2008) $
 */

//modifiee



/**
 */
public class Step {
    private int axis;
    private NodeTest nodeTest;
    private Element[] predicates;

 
    /**
     * Constructor for Step.
     * @param axis int
     * @param nodeTest NodeTest
     * @param predicates Element[]
     */
    public Step(int axis, NodeTest nodeTest, Element[] predicates) {
        this.axis = axis;
        this.nodeTest = nodeTest;
        this.predicates = predicates;
    }
 
    /**
     * Method getAxis.
     * @return int
     */
    public int getAxis() {
        return axis;
    }

 
    /**
     * Method getNodeTest.
     * @return NodeTest
     */
    public NodeTest getNodeTest() {
        return nodeTest;
    }

   
    /**
     * Method getPredicates.
     * @return Element[]
     */
    public Element[] getPredicates() {
        return predicates;
    }


    /**
     * Method toString.
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        int axis = getAxis();
        if (axis == Compiler.AXIS_CHILD) {
            buffer.append(nodeTest);
        }
        else if (axis == Compiler.AXIS_ATTRIBUTE) {
            buffer.append('@');
            buffer.append(nodeTest);
        }
       
        else if (axis == Compiler.AXIS_SELF
                && nodeTest instanceof NodeTypeTest
                && ((NodeTypeTest) nodeTest).getNodeType()
                    == Compiler.NODE_TYPE_NODE) {
            buffer.append(".");
        }
        else if (axis == Compiler.AXIS_PARENT
                && nodeTest instanceof NodeTypeTest
                && ((NodeTypeTest) nodeTest).getNodeType()
                    == Compiler.NODE_TYPE_NODE) {
            buffer.append("..");
        }
        else if (axis == Compiler.AXIS_DESCENDANT_OR_SELF
                && nodeTest instanceof NodeTypeTest
                && ((NodeTypeTest) nodeTest).getNodeType()
                    == Compiler.NODE_TYPE_NODE
                && (predicates == null || predicates.length == 0)) {
            buffer.append("");
        }
        else {
            buffer.append(axisToString(axis));
            buffer.append("::");
            buffer.append(nodeTest);
        }
        Element[] predicates = getPredicates();
        if (predicates != null) {
            for (int i = 0; i < predicates.length; i++) {
                buffer.append('[');
                buffer.append(predicates[i]);
                buffer.append(']');
            }
        }
        return buffer.toString();
    }
 
    /**
     * Method axisToString.
     * @param axis int
     * @return String
     */
    public static String axisToString(int axis) {
        switch (axis) {
            case Compiler.AXIS_SELF :
                return "self";
            case Compiler.AXIS_CHILD :
                return "child";
            case Compiler.AXIS_PARENT :
                return "parent";
            case Compiler.AXIS_ANCESTOR :
                return "ancestor";
            case Compiler.AXIS_ATTRIBUTE :
                return "attribute";
            case Compiler.AXIS_NAMESPACE :
                return "namespace";
            case Compiler.AXIS_PRECEDING :
                return "preceding";
            case Compiler.AXIS_FOLLOWING :
                return "following";
            case Compiler.AXIS_DESCENDANT :
                return "descendant";
            case Compiler.AXIS_ANCESTOR_OR_SELF :
                return "ancestor-or-self";
            case Compiler.AXIS_FOLLOWING_SIBLING :
                return "following-sibling";
            case Compiler.AXIS_PRECEDING_SIBLING :
                return "preceding-sibling";
            case Compiler.AXIS_DESCENDANT_OR_SELF :
                return "descendant-or-self";
            default:
                return "UNKNOWN";
        }
    }

	 
}
