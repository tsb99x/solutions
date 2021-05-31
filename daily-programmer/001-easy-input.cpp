#include "utils.hpp"

#include <fstream>

using namespace std;

int main()
{
    string name     = input("your name > ");
    string years    = input("your age > ");
    string username = input("your username > ");

    string msg = "your name is " + name + ", "
                 "you are " + years + " years old, "
                 "and your username is " + username;

    fstream log("tmp/log.txt", fstream::app);

    cout << msg << endl;
    log << msg << endl;
}
