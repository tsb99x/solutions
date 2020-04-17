#include <string>
#include <iostream>
#include <fstream>

using namespace std;

string prompt(const string &invite)
{
    string res;
    cout << invite;
    getline(cin, res);
    return res;
}

int main()
{
    string name = prompt("Input name > ");
    string age = prompt("Input age > ");
    string username = prompt("Input username > ");

    cout << "your name is (" << name
         << "), you are (" << age
         << ") years old, and your username is (" << username << ")" << endl;

    ofstream os("log.txt", ios_base::app);
    if (!os.is_open()) {
        cerr << "Failed to open 'log.txt' file";
        return -1;
    }

    os << "name: " << name
       << ", age: " << age
       << ", username: " << username << endl;
}
