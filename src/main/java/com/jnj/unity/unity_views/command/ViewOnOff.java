package com.jnj.unity.unity_views.command;

import org.kohsuke.args4j.Option;

import com.gemstone.gemfire.cache.client.Pool;
import com.jnj.adf.client.api.IBiz;
import com.jnj.adf.grid.data.schema.ProcessorTypes;
import com.jnj.adf.grid.manager.GridManager;
import com.jnj.adf.grid.support.context.ADFMetaService;

/**
 * Command class that view processor of async listener works or not.
 * 
 * @author sli172
 *
 */
@SuppressWarnings("deprecation")
public class ViewOnOff extends CmdBase {

	@Option(name = "-enable", usage = "whether enable view processor in aysnc listener, default is true.")
	private boolean enabled = false;

	protected void doProcess() {
		Pool pool = GridManager.getInstance().getGemFirePool(grid);
		IBiz.remote(ADFMetaService.class).onServers(pool).setAQProcessorEnabled(ProcessorTypes.VIEW, enabled);

		System.out.println("Finish to switch view processor to " + enabled + "!");
		System.out.println(
				"You check using oql: gfsh>query --query=\"select e.key.toString(),e.value.toString() from /_ADF/META.entrySet e where e.key.property='AQ_PROCESSOR'\"");
		System.exit(0);
	}

	public static void main(String[] args) {
		ViewOnOff inst = new ViewOnOff();
		inst.process(args);
	}
}
