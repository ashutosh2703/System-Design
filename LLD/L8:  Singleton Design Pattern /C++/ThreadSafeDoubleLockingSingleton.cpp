#include<iostream>
#include <mutex>
#include <atomic>

using namespace std;

class Singleton {
private:
    static atomic<Singleton*> instance; // atomic: unlocked first check must not race with the write
    static mutex mtx;

    Singleton() { 
        cout << "Singleton Constructor Called!" << endl; 
    }
 
public:
    // Double check locking..
    static Singleton* getInstance() {
        if (instance == nullptr) {  // First check (no locking)
            lock_guard<mutex> lock(mtx);  // Lock only if needed
            if (instance == nullptr) {  // Second check (after acquiring lock)
                instance = new Singleton();
            }
        }
        return instance;
    }
};
 
// Initialize static members
atomic<Singleton*> Singleton::instance{nullptr};
mutex Singleton::mtx;

int main() {
    Singleton* s1 = Singleton::getInstance();
    Singleton* s2 = Singleton::getInstance();

    cout << (s1 == s2) << endl;
}