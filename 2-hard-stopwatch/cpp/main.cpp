#include "utils.hpp"

#include <chrono>

using namespace std;
using namespace chrono;
using namespace utils;

int main()
{
    cout << "Use [s] to start, [p] to stop, [l] for lap, [x] to exit" << newl;

    time_point<system_clock> start;
    time_point<system_clock> last_lap;

    while (true) {
        string input = prompt("> ");

        if (input.empty() || input.size() > 1) {
            cerr << "Input must consist of one char" << endl;
        } else if (input == "s") {
            start = system_clock::now();
            last_lap = start;

            using days = duration<long long int, std::ratio<3600 * 24>>;

            auto dur = start.time_since_epoch();
            auto days_val = duration_cast<days>(dur);
            auto hours_val = duration_cast<hours>(dur -= days_val);
            auto minutes_val = duration_cast<minutes>(dur -= hours_val);
            auto seconds_val = duration_cast<seconds>(dur -= minutes_val);

            if (last_lap != start)
                cout << "Dropping previous results" << newl;

            cout << "Stopwatch started at "
                 << hours_val.count() << "h "
                 << minutes_val.count() << "m "
                 << seconds_val.count() << "s " << newl;
        } else if (input == "p") {
            cout << "UNDEFINED" << endl;
        } else if (input == "l") {
            auto current_time = system_clock::now();
            auto lap_time = current_time - last_lap;
            last_lap = current_time;

            auto mins = duration_cast<minutes>(lap_time);
            auto secs = duration_cast<seconds>(lap_time -= mins);
            auto ms = duration_cast<milliseconds>(lap_time -= secs);

            cout << "Lap: " << mins.count() << "m "
                 << secs.count() << "s "
                 << ms.count() << "ms" << newl;
        } else if (input == "x") {
            cout << "closing app..." << endl;
            exit(EXIT_SUCCESS);
        }
    }
}
