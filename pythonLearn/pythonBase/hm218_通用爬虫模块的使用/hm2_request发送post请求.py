import requests
import json

data = {"from": "en", "to": "zh", "query": "good", "transtype": "translang", "simple_means_flag": "3", "sign": "262931.57378", "token": "79de89e6870457404fe6a29d5d895c3a"}
headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36", "Cookie": "BIDUPSID=9D92BDBFE75622294CFA613DCDEBF1F5; BAIDUID=422E065F65C4C2E030B26F1719A716F6:FG=1; PSTM=1540002944; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; H_PS_PSSID=1427_21083_18560_26350_27245_20929; PSINO=7; locale=zh; to_lang_often=%5B%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%2C%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%5D; REALTIME_TRANS_SWITCH=1; FANYI_WORD_SWITCH=1; HISTORY_SWITCH=1; SOUND_SPD_SWITCH=1; SOUND_PREFER_SWITCH=1; Hm_lvt_64ecd82404c51e03dc91cb9e8c025574=1540006654; Hm_lpvt_64ecd82404c51e03dc91cb9e8c025574=1540006654; from_lang_often=%5B%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%2C%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%5D"}
response = requests.post("https://fanyi.baidu.com/v2transapi", data=data, headers=headers)
print(response.content.decode('utf-8'))
j = json.loads(response.content.decode('utf-8'))
print(j["trans_result"]["data"][0]["dst"])
j = json.dumps(j, ensure_ascii=False, indent=2)
print(j)

