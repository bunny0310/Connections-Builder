package com.cb.cache;

import java.util.HashMap;

public class LRUCache<K, V> {
    //create dummy nodes for head and tail
    Node<K, V> head;
    Node<K, V> tail;

    //declare a hashmap
    HashMap<K, Node> map;

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

    public V get(K key) {
        Node ret = map.containsKey(key) ? map.get(key) : null;

        if(ret != null) {
            removeNode(ret);
            addNode(ret);
            return (V) ret.val;
        }
        return null;
    }

    public void put(K key, V val) {
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

class Node<K, V> {
    K key;
    V val;
    Node<K, V> prev;
    Node<K, V> next;

    public Node() {

    }

    public Node(K key, V val) {
        this.key = key;
        this.val = val;
    }
}