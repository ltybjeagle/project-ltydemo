package com.liuty.maven.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU缓存DEMO
 */
public class LRUcache {

    private Node head;
    private Node end;
    private int limit;
    private Map<String, Node> hashMap;

    public LRUcache(int limit) {
        this.limit = limit;
        hashMap = new HashMap<>();
    }

    public String get(String key) {
        Node node = hashMap.get(key);
        if (node == null) {
            return null;
        }
        refreshNode(node);
        return node.value;
    }

    public void put(String key, String value) {
        Node node = hashMap.get(key);
        if (node == null) {
            if (hashMap.size() >= limit) {
                removeNode(head);
                hashMap.remove(head.key);
            }
            node = new Node(key, value);
            addNode(node);
            hashMap.put(key, node);
        } else {
            node.value = value;
            refreshNode(node);
        }
    }

    public void remove(String key) {
        Node node = hashMap.get(key);
        if (node != null) {
            removeNode(node);
            hashMap.remove(key);
        }
    }

    /**
     * 删除节点
     * @param node
     * @return
     */
    private String removeNode(Node node) {
        if (end == node) {
            end = node.pre;
        } else if (head == node) {
            head = node.next;
        } else {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        return node.key;
    }

    /**
     * 尾部添加节点
     * @param node
     */
    private void addNode(Node node) {
        if (end != null) {
            end.next = node;
            node.pre = end;
            node.next = null;
        }
        end = node;
        if (head == null) {
            head = node;
        }
    }

    /**
     * 刷新被访问的节点位置
     * @param node
     */
    private void refreshNode(Node node) {
        if (end == node) {
            return ;
        }
        removeNode(node);
        addNode(node);
    }

    class Node {
        private String key;
        private String value;
        private Node pre;
        private Node next;
        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
