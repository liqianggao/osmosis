package com.bretth.osmosis.sort;

import java.util.Comparator;

import com.bretth.osmosis.container.ChangeContainer;
import com.bretth.osmosis.sort.impl.FileBasedSort;
import com.bretth.osmosis.sort.impl.ReleasableIterator;
import com.bretth.osmosis.task.ChangeSink;
import com.bretth.osmosis.task.ChangeSinkChangeSource;


/**
 * A change stream filter that sorts changes. The sort order is specified by
 * comparator provided during instantiation.
 * 
 * @author Brett Henderson
 */
public class ChangeSorter implements ChangeSinkChangeSource {
	private FileBasedSort<ChangeContainer> fileBasedSort;
	private ChangeSink sink;
	
	
	/**
	 * Creates a new instance.
	 * 
	 * @param comparator
	 *            The comparator to use for sorting.
	 */
	public ChangeSorter(Comparator<ChangeContainer> comparator) {
		fileBasedSort = new FileBasedSort<ChangeContainer>(comparator, true);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void process(ChangeContainer change) {
		fileBasedSort.add(change);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void setChangeSink(ChangeSink sink) {
		this.sink = sink;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void complete() {
		ReleasableIterator<ChangeContainer> iterator = null;
		
		try {
			iterator = fileBasedSort.iterate();
			
			while (iterator.hasNext()) {
				sink.process(iterator.next());
			}
			
			sink.complete();
		} finally {
			if (iterator != null) {
				iterator.release();
			}
		}
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void release() {
		fileBasedSort.release();
		sink.release();
	}
}