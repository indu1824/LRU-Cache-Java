import java.util.HashMap;
import java.util.Scanner;

// Node class for Doubly Linked List
class Node {
    int key, value;
    Node prev, next;

    Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

// LRU Cache class
class LRUCache {
    private int capacity;
    private HashMap<Integer, Node> map;
    private Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();

        // Dummy head and tail nodes
        head = new Node(0, 0);
        tail = new Node(0, 0);

        head.next = tail;
        tail.prev = head;
    }

    // Remove node
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Insert at front (most recent)
    private void insert(Node node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    // Get operation
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        Node node = map.get(key);
        remove(node);
        insert(node);

        return node.value;
    }

    // Put operation
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            remove(map.get(key));
        }

        if (map.size() == capacity) {
            Node lru = tail.prev;
            remove(lru);
            map.remove(lru.key);
        }

        Node newNode = new Node(key, value);
        insert(newNode);
        map.put(key, newNode);
    }

    // Display cache
    public void display() {
        Node current = head.next;
        System.out.print("Cache State: ");
        while (current != tail) {
            System.out.print("[" + current.key + ":" + current.value + "] ");
            current = current.next;
        }
        System.out.println();
    }
}

// Main class
public class LRUCacheApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== LRU Cache Simulation ===");
        System.out.print("Enter cache capacity: ");
        int capacity = sc.nextInt();

        LRUCache cache = new LRUCache(capacity);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Put (Insert)");
            System.out.println("2. Get (Access)");
            System.out.println("3. Display Cache");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter key: ");
                    int key = sc.nextInt();
                    System.out.print("Enter value: ");
                    int value = sc.nextInt();
                    cache.put(key, value);
                    System.out.println("Inserted!");
                    break;

                case 2:
                    System.out.print("Enter key: ");
                    int k = sc.nextInt();
                    int result = cache.get(k);
                    if (result == -1) {
                        System.out.println("Key not found!");
                    } else {
                        System.out.println("Value: " + result);
                    }
                    break;

                case 3:
                    cache.display();
                    break;

                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
