package com.nhl.link.etl.runtime.task.delete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.cayenne.ObjectContext;

import com.nhl.link.etl.EtlTask;
import com.nhl.link.etl.Execution;
import com.nhl.link.etl.runtime.task.sourcekeys.SourceKeysTask;

/**
 * @since 1.3
 */
public class SourceMatcher<T> {

	private EtlTask keysSubtask;

	public SourceMatcher(EtlTask keysSubtask) {
		this.keysSubtask = keysSubtask;
	}

	public List<T> match(ObjectContext context, Map<Object, T> mappedTargets) {

		Set<Object> sourceKeys = sourceKeys();
		List<T> matched = new ArrayList<>();

		for (Entry<Object, T> e : mappedTargets.entrySet()) {
			if (!sourceKeys.contains(e.getKey())) {
				matched.add(e.getValue());
			}
		}

		return matched;
	}

	@SuppressWarnings("unchecked")
	private Set<Object> sourceKeys() {
		// TODO: per-Execution caching...

		Execution exec = keysSubtask.run();

		Set<Object> keys = (Set<Object>) exec.getAttribute(SourceKeysTask.RESULT_KEY);
		if (keys == null) {

		}

		return keys;
	}
}