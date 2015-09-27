package com.zarusz.control.repository;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Tomasz on 9/17/2015.
 */
public class CollectionInit {

    public static <E> void Fetch(Collection<E> nodes, Function<E, Collection<E>> selector, Consumer<E> action) {
        for (E node : nodes) {
            Fetch(node, selector, action);
        }
    }

    public static <E> void Fetch(Collection<E> nodes, Function<E, Collection<E>> selector) {
        Fetch(nodes, selector, null);
    }

    public static <E> void Fetch(E node, Function<E, Collection<E>> selector, Consumer<E> action) {
        if (action != null) {
            action.accept(node);
        }
        Collection<E> children = selector.apply(node);
        for (E child : children) {
            Fetch(child, selector, action);
        }
    }

    public static <E> void Fetch(E node, Function<E, Collection<E>> selector) {
        Fetch(node, selector, null);
    }
}
