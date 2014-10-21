package com.nhl.link.etl;

/**
 * An abstraction of a runnable task.
 */
public interface EtlTask {

	/**
	 * Executes a task with no {@link SyncToken} context. Some ETL jobs would
	 * always do a full sync, so this method is appropriate for them.
	 * 
	 * @since 1.1
	 */
	Execution run();

	/**
	 * Executes the task returning {@link Execution} object that can be used by
	 * the caller to analyze the results. Currently all task implementations are
	 * synchronous, so this method returns only on task completion.
	 */
	// TODO: Implement asynchronous tasks where {@link Execution} can be
	// use to watch progress, and gives the ability to cancel the task mid-way.
	Execution run(SyncToken token);

}
