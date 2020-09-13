package com.cb.cache;

import java.util.HashMap;

public class LRUCache<T> {
    //create dummy nodes for head and tail
    Node<T> head;
    Node<T> tail;

    //declare a hashmap
    HashMap<Integer, Node> map;

    //declare a variable to store the capacity allowed
    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.head = new Node();
        this.tail = new Node();
        this.head.next = this.tail;
        this.tail.prev = this.head;
        this.map = new HashMap();
    }

    public void addNode(Node node) {
        head.next.prev = node;
        node.next = head.next;
        head.next = node;
        node.prev = head;
    }

    public void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public T get(int key) {
        Node ret = map.containsKey(key) ? map.get(key) : null;

        if(ret != null) {
            removeNode(ret);
            addNode(ret);
            return (T) ret.val;
        }
        return null;
    }

    public void put(int key, T val) {
        Node ret = map.containsKey(key) ? map.get(key) : null;

        if(ret != null) {
            removeNode(ret);
            ret.val = val;
            map.put(key, ret);
            addNode(ret);
        }
        else {
            if(capacity == map.size()) {
                map.remove(tail.prev.key);
                removeNode(tail.prev);
            }
            Node node = new Node(key, val);
            addNode(node);
            map.put(key, node);
        }
    }
}

class Node<T> {
    int key;
    T val;
    Node<T> prev;
    Node<T> next;

    public Node() {

    }

    public Node(int key, T val) {
        this.key = key;
        this.val = val;
    }
}