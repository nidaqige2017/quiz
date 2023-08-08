package cn.test.myproject.dataStructure;

import lombok.extern.slf4j.Slf4j;

/**
 * 请用链表实现一个先进先出的队列
 */
public class LinkedListImplQueue {

    static LinkedListQueue queue = new LinkedListQueue<Integer>();

    @Slf4j
    private static class Offer extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                queue.offer(i + 1);
                log.info("入队 {} 现在队列中的数量 {}", i + 1,queue.size());
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Slf4j
    private static class Poll extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                Integer poll = (Integer) queue.poll();
                log.info("弹栈 :{} 现在队列数量: {}", poll,queue.size());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Offer().start();
        new Poll().start();
    }
}

interface Queue<E> {

    /**
     * 入队
     */
    void offer(E element);

    /**
     * 出队
     */
    E poll();

    /**
     * 查看队首元素
     */
    E peek();

    /**
     * 判断队列是否为空
     */
    boolean isEmpty();

    int size();
}

class LinkedListQueue<E> implements Queue<E> {
    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E element) {
            this.element = element;
        }
    }

    //尾节点进，首节点出
    private Node<E> head;
    private Node<E> last;
    private int size;

    @Override
    public void offer(E element) {
        //1. 把element封装成节点对象
        Node<E> node = new Node<>(element);
        //2. 处理链表为空的情况,设置node节点为链表的首尾节点
        if (null == head) head = node;
        //3. 处理链表不为空的情况
        // 把node节点添加到last的后面
        else last.next = node;
        //把node节点设置为链表的尾节点
        last = node;
        size++;
    }

    @Override
    public E poll() {
        //处理队列为空的情况
        if (null == head) return null;
        //保存出节点的值
        E element = head.element;
        //保存新首节点
        Node<E> nextNode = head.next;
        //取消头节点和新头节点的连线
        head.next = null;
        //设置新首节点
        head = nextNode;
        size--;
        return element;
    }

    @Override
    public E peek() {
        //处理队列为空的情况
        if (null == head) return null;
        return head.element;
    }

    @Override
    public boolean isEmpty() {
        return null == head;
    }

    @Override
    public int size() {
        return size;
    }
}