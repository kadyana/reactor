/*
 * Copyright (c) 2011-2015 Pivotal Software Inc, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package reactor.core.processor;

import org.reactivestreams.Processor;
import reactor.fn.Consumer;

/**
 * A base processor
 *
 * @author Stephane Maldini
 */
public abstract class ReactorProcessor<E> implements Processor<E, E>, Consumer<E> {

	protected static final int DEFAULT_BUFFER_SIZE = 1024;
	protected static final int SMALL_BUFFER_SIZE = 32;

	protected final  ClassLoader   context = new ClassLoader(Thread.currentThread()
			.getContextClassLoader()) {
	};

	@Override
	public final void accept(E e) {
		onNext(e);
	}

	/**
	 * A Container for data passed on this processor
	 *
	 * @param <E> the type of the contained value
	 */
	protected static class MutableSignal<E> {
		protected SType     type;
		protected Throwable throwable;
		protected E         value;

		public MutableSignal(SType type, Throwable throwable, E value) {
			this.type = type;
			this.throwable = throwable;
			this.value = value;
		}

	}

	/**
	 * The type of signal (error|next|complete)
	 */
	protected enum SType {
		ERROR,
		NEXT,
		COMPLETE
	}
}