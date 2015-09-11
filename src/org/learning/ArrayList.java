package org.learning;


import java.util.*;

public class ArrayList<T> implements List<T> {

    private T[] m = (T[]) new Object[1];

    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
        for (int i = 0; i < size; i++) {
            if (m[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public Object[] toArray() {
        final T[] newM = (T[]) new Object[this.size()];
        System.arraycopy(m, 0, newM, 0, this.size());
        return newM;
    }

    @Override
    public <T1> T1[] toArray(final T1[] a) {
        if (a.length < size)
            return (T1[]) Arrays.copyOf(m, size, a.getClass());

        System.arraycopy(m, 0, a, 0, size);

        if (a.length > size)
            a[size] = null;

        return a;
    }

    @Override
    public boolean add(final T t) {
        if (m.length == size) {
            final T[] oldM = m;
            m = (T[]) new Object[this.size() * 2];
            System.arraycopy(oldM, 0, m, 0, oldM.length);
        }
        m[size++] = t;
        return true;
    }

    @Override
    public boolean remove(final Object o) {
        for (int i = 0; i < size(); i++) {
            if (m[i].equals(o)) {
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        for (final Object item : this) {
            if (!c.contains(item)) this.remove(item);
        }
        return true;
    }

    @Override
    public void clear() {
        m = (T[]) new Object[1];
        size = 0;
    }

    @Override
    public T remove(final int index) {
        final T element = m[index];
        if (index != this.size() - 1)
            System.arraycopy(m, index + 1, m, index, this.size() - index - 1);
        size--;
        return element;
    }

    @Override
    public List<T> subList(final int start, final int end) {
        return null;
    }

    @Override
    public ListIterator listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public ListIterator listIterator(final int index) {
        return new ElementsIterator(index);
    }

    /**
     * Returns the index of the last occurrence of the specified element in this list,
     * or -1 if this list does not contain the element.
     * More formally, returns the highest index i such that (o==null ? get(i)==null : o.equals(get(i))),
     * or -1 if there is no such index.
     *
     * @param o - element to search for
     * @return the index of the last occurrence of the specified element in this list,
     * or -1 if this list does not contain the element
     */
    @Override
    public int lastIndexOf(final Object target) {

        if (target == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (m[i] == null)
                    return i;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (m[i].equals(target))
                    return i;
            }
        }

        return -1;
    }

    @Override
    public int indexOf(final Object target) {
        if (target == null) {
            for (int i = 0; i < size; i++) {
                if (m[i] == null)
                    return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (m[i].equals(target))
                    return i;
            }
        }

        return -1;
    }

    @Override
    public void add(final int index, final T element) {
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (m.length == size) {
            final T[] oldM = m;
            m = (T[]) new Object[this.size() * 2];

            if (index != 0)
                System.arraycopy(oldM, 0, m, 0, index);

            if (index != size)
                System.arraycopy(oldM, index, m, index + 1, size - index);

        } else {

            if (index != size)
                System.arraycopy(m, index, m, index + 1, size - index);

        }

        m[index] = element;
        size++;

    }

    @Override
    public boolean addAll(final int index, final Collection elements) {

        if (index > size) {
            throw new IndexOutOfBoundsException();
        }

        int addSize = elements.size();

        if (m.length < (size + addSize)) {

            final T[] oldM = m;

            m = (T[]) new Object[this.size() > (addSize * 2) ? this.size() * 2 : this.size() + 2 * addSize];

            if (index != 0)
                System.arraycopy(oldM, 0, m, 0, index);

            if (index != size)
                System.arraycopy(oldM, index, m, index + addSize, addSize);

        } else {

            if (index != m.length)
                System.arraycopy(m, index, m, index + addSize, addSize);

        }

        System.arraycopy(elements.toArray(), 0, m, index, addSize);

        size += addSize;

        return true;
    }

    @Override
    public T set(final int index, final T element) {
        m[index] = element;
        return element;
    }

    @Override
    public T get(final int index) {
        return m[index];
    }

    private class ElementsIterator implements ListIterator<T> {

        private int index;

        private int last = -1;

        public ElementsIterator() {
            this(0);
        }

        public ElementsIterator(final int index) {
            // BEGIN (write your solution here)
            this.index = index;
            // END
        }

        @Override
        public boolean hasNext() {
            return ArrayList.this.size() > index;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            last = index;
            return ArrayList.this.m[index++];
        }

        @Override
        public void add(final T element) {
            ArrayList.this.add(index, element);
        }

        /**
         * Replaces the last element returned by next() or previous() with the specified element (optional operation).
         * This call can be made only if neither remove() nor add(E) have been called after the last call
         * to next or previous.
         *
         * @param element
         */
        @Override
        public void set(final T element) {
            // BEGIN (write your solution here)
            if (last == -1) {
                throw new IllegalStateException();
            }
            m[last] = element;
            // END
        }

        /**
         * Returns the index of the element that would be returned by a subsequent call to previous().
         * (Returns -1 if the list iterator is at the beginning of the list.)
         *
         * @return the index of the element that would be returned by a subsequent call to previous,
         * or -1 if the list iterator is at the beginning of the list
         */
        @Override
        public int previousIndex() {
            // BEGIN (write your solution here)
            return index - 1;
            // END
        }

        /**
         * Returns the index of the element that would be returned by a subsequent call to next().
         * (Returns list size if the list iterator is at the end of the list.)
         */
        @Override
        public int nextIndex() {
            // BEGIN (write your solution here)
            return index;
            // END
        }

        /**
         * Returns true if this list iterator has more elements when traversing the list in the reverse direction.
         * (In other words, returns true if previous() would return an element rather than throwing an exception.)
         *
         * @return true if the list iterator has more elements when traversing the list in the reverse direction
         */
        @Override
        public boolean hasPrevious() {
            // BEGIN (write your solution here)
            return index > 0;
            // END
        }

        /**
         * Returns the previous element in the list and moves the cursor position backwards.
         * This method may be called repeatedly to iterate through the list backwards,
         * or intermixed with calls to next() to go back and forth.
         * (Note that alternating calls to next and previous will return the same element repeatedly.)
         *
         * @return the previous element in the list
         * @throws NoSuchElementException - if the iteration has no previous element
         */
        @Override
        public T previous() {
            // BEGIN (write your solution here)
            if (!hasPrevious())
                throw new NoSuchElementException();

            last = --index;
            return m[index];
            // END
        }

        @Override
        public void remove() {
            if (last == -1)
                throw new IllegalStateException();
            ArrayList.this.remove(last);
            index--;
            last = -1;
        }

    }


}
