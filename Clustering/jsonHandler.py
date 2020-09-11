import json

import requests

jsonFileName = "events.json"


def fetch_json_dict():
    response = requests.get("https://covid-dashboard.aminer.cn/api/dist/events.json", verify=False)

    json_file = open(jsonFileName, "w")
    json_file.write(response.text)
    json_file.close()

    return response.json()


def fetch_event_list():
    complete_list = fetch_json_dict()["datas"]
    return [event for event in complete_list if event["type"] == "event"]


def save_event_list(filename="event_list.json"):
    file = open(filename, "w")
    json.dump(fetch_event_list(), file)
    file.close()


if __name__ == "__main__":
    save_event_list()
