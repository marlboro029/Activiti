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
package org.activiti5.engine.impl.cmd;

import java.io.Serializable;

import org.activiti5.engine.ActivitiException;
import org.activiti5.engine.ActivitiObjectNotFoundException;
import org.activiti5.engine.form.StartFormData;
import org.activiti5.engine.impl.form.FormEngine;
import org.activiti5.engine.impl.form.StartFormHandler;
import org.activiti5.engine.impl.interceptor.Command;
import org.activiti5.engine.impl.interceptor.CommandContext;
import org.activiti5.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti5.engine.repository.ProcessDefinition;

/**
 * @author Tom Baeyens
 * @author Joram Barrez
 */
public class GetRenderedStartFormCmd implements Command<Object>, Serializable {

    private static final long serialVersionUID = 1L;
    protected String processDefinitionId;
    protected String formEngineName;

    public GetRenderedStartFormCmd(String processDefinitionId, String formEngineName) {
        this.processDefinitionId = processDefinitionId;
        this.formEngineName = formEngineName;
    }

    public Object execute(CommandContext commandContext) {
        ProcessDefinitionEntity processDefinition = commandContext.getProcessEngineConfiguration().getDeploymentManager()
                .findDeployedProcessDefinitionById(processDefinitionId);
        if (processDefinition == null) {
            throw new ActivitiObjectNotFoundException("Process Definition '" + processDefinitionId + "' not found", ProcessDefinition.class);
        }
        StartFormHandler startFormHandler = processDefinition.getStartFormHandler();
        if (startFormHandler == null) {
            return null;
        }

        FormEngine formEngine = commandContext.getProcessEngineConfiguration().getFormEngines().get(formEngineName);

        if (formEngine == null) {
            throw new ActivitiException("No formEngine '" + formEngineName + "' defined process engine configuration");
        }

        StartFormData startForm = startFormHandler.createStartFormData(processDefinition);

        return formEngine.renderStartForm(startForm);
    }
}
