package Locks;

class Consumer extends Thread {
    private Message message;

    public Consumer(Message message) {
        this.message = message;
    }

    public void run() {
        int value = 0;
        for (int i = 0; i < 10; i++) {
            value = message.get();
            System.out.println("Consumer "
                    + " got: " + value);
        }
    }
}



