/*

    https://www.reddit.com/r/dailyprogrammer/comments/pihtx/intermediate_challenge_1/

    [intermediate] challenge #1

    create a program that will allow you to enter events organizable by hour.
    There must be menu options of some form, and you must be able to easily edit, add, and delete events without directly changing the source code.

    (note that by menu i dont necessarily mean gui.
    as long as you can easily access the different options and receive prompts and instructions telling you how to use the program, it will probably be fine)

*/

#include "utils.hpp"

#include <algorithm>

using namespace std;

struct event {
    int hour;
    string name;
};

void cmd_help()
{
    cout << "command list:\n"
            "\t- exit                 -- quit application"
            "\t- help                 -- output current message\n"
            "\t- list                 -- ordered list of all events\n"
            "\t- create {HOUR} {NAME} -- add new event\n"
            "\t- update {INDEX}       -- edit specific event\n"
            "\t- delete {INDEX}       -- remove specific event" << endl;
}

void cmd_list(
    const vector<event> &events
) {
    if (events.empty())
        cout << "no events added yet" << endl;

    int idx = 0;
    for (auto &it : events)
        cout << "\t[" << idx++ << "]: "
                "hour = " << it.hour << ", "
                "name = " << it.name << endl;
}

bool comparator(
    const event &a,
    const event &b
) {
    return a.hour < b.hour;
}

void cmd_create(
    vector<event> &events,
    const vector<string> &args
) {
    if (args.size() < 3) {
        cout << "[!] create command format is: create {HOUR} {NAME}" << endl;
        return;
    }

    auto opt_hour = read_opt<int>(args[1]);
    if (!opt_hour.has_value()) {
        cout << "[!] hour argument must be an int" << endl;
        return;
    }
    int hour = opt_hour.value();

    if (hour < 0 || hour > 23) {
        cout << "[!] hour argument must be in [0, 23], but was "
             << to_string(hour) << endl;
        return;
    }

    events.push_back({ hour, args[2] });
    sort(begin(events), end(events), comparator);
    cout << "event was successfully added" << endl;
}

void cmd_update(
    vector<event> &events,
    const vector<string> &args
) {
    if (args.size() < 2) {
        cout << "[!] update command format is: update {INDEX}" << endl;
        return;
    }

    auto opt_idx = read_opt<int>(args[1]);
    if (!opt_idx.has_value()) {
        cout << "[!] idx argument must be an int" << endl;
        return;
    }
    int idx = opt_idx.value();

    if (idx < 0) {
        cout << "[!] idx argument must be greater or equal to 0" << endl;
        return;
    }

    if (static_cast<size_t>(idx) >= events.size()) {
        cout << "[!] idx of " << idx << " does not exist" << endl;
        return;
    }

    event evt = events[idx];

    string hour_str = input("input hour (" + to_string(evt.hour) + ") > ");
    if (!hour_str.empty()) {
        auto opt_hour = read_opt<int>(hour_str);
        if (!opt_hour.has_value()) {
            cout << "[!] hour argument must be an int" << endl;
            return;
        }
        int hour = opt_hour.value();

        if (hour < 0 || hour > 23) {
            cout << "hour argument must be in [0, 23], but was " << hour << endl;
            return;
        }

        evt.hour = hour;
    }

    string name = input("input name (" + evt.name + ") > ");
    if (!name.empty())
        evt.name = name;

    events.erase(begin(events) + idx);
    events.push_back(evt);

    sort(begin(events), end(events), comparator);
    cout << "event was successfully updated" << endl;
}

void cmd_delete(
    vector<event> &events,
    const vector<string> &args
) {
    if (args.size() < 2) {
        cout << "[!] delete command format is: delete {INDEX}" << endl;
        return;
    }

    auto opt_idx = read_opt<int>(args[1]);
    if (!opt_idx.has_value()) {
        cout << "[!] idx argument must be an int" << endl;
        return;
    }
    int idx = opt_idx.value();

    if (idx < 0) {
        cout << "[!] idx argument must be greater or equal to 0" << endl;
        return;
    }

    if (static_cast<size_t>(idx) >= events.size()) {
        cout << "[i] idx of " << idx << " does not exist" << endl;
        return;
    }

    events.erase(begin(events) + idx);
    cout << "event was successfully removed" << endl;
}

int main()
{
    vector<event> events;

    while (true) {
        string command = input("> ");
        vector<string> args = split(command, ' ');
        if (args.empty())
            continue;

        if      (args[0] == "exit"  ) return EXIT_SUCCESS;
        else if (args[0] == "help"  ) cmd_help();
        else if (args[0] == "list"  ) cmd_list(events);
        else if (args[0] == "create") cmd_create(events, args);
        else if (args[0] == "delete") cmd_delete(events, args);
        else if (args[0] == "update") cmd_update(events, args);
        else cout << "unknown command `" << args[0] << "`" << endl;
    }
}
