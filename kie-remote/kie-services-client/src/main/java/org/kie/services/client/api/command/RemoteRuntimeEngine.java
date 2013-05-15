package org.kie.services.client.api.command;

import org.drools.core.command.CommandService;
import org.drools.core.command.impl.CommandBasedStatefulKnowledgeSession;
import org.jbpm.services.task.impl.command.CommandBasedTaskService;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.task.TaskService;
import org.kie.internal.task.api.TaskCommandExecutor;

public class RemoteRuntimeEngine implements RuntimeEngine {

	private String url;
	private String deploymentId;
	private String contextId;
	
	public RemoteRuntimeEngine(String url, String deploymentId, String contextId) {
		this.url = url;
		this.deploymentId = deploymentId;
		this.contextId = contextId;
	}
	
	public KieSession getKieSession() {
		String url = this.url + "/session/execute";
		if (this.contextId != null) {
			url += "?contextId=" + contextId;
		}
		CommandService commandService = new RemoteCommandService(url, deploymentId);
		return new CommandBasedStatefulKnowledgeSession(commandService);
	}

	public TaskService getTaskService() {
		String url = this.url + "/task/execute";
		if (this.contextId != null) {
			url += "?contextId=" + contextId;
		}
		TaskCommandExecutor executor = new RemoteTaskCommandExecutor(url, deploymentId);
		return new CommandBasedTaskService(executor);
	}

}
