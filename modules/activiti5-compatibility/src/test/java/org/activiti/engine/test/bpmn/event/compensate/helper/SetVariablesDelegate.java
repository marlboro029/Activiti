/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.engine.test.bpmn.event.compensate.helper;

import java.util.HashMap;
import java.util.Map;

import org.activiti5.engine.delegate.DelegateExecution;
import org.activiti5.engine.delegate.JavaDelegate;

/**
 * @author Daniel Meyer
 */
public class SetVariablesDelegate implements JavaDelegate {

    public static Map<Object, Integer> variablesMap = new HashMap<Object, Integer>();

    // activiti creates a single instance of the delegate
    protected int lastInt = 0;

    public void execute(DelegateExecution execution) throws Exception {
        Object nrOfCompletedInstances = execution.getVariableLocal("nrOfCompletedInstances");
        variablesMap.put(nrOfCompletedInstances, lastInt);
        execution.setVariableLocal("variable", lastInt);
        lastInt++;
    }

}
