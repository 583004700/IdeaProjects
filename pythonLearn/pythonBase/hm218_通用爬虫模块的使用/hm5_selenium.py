from selenium import webdriver

driver = webdriver.Chrome("C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe")
# driver = webdriver.PhantomJS()

driver.get("https://www.baidu.com")

# 设置宽高
# driver.set_window_size(1920, 1080)
driver.maximize_window()
# 进行截屏
driver.save_screenshot("baidu.png")

driver.find_element_by_id("kw").send_keys("你好")
driver.find_element_by_id("su").click()

# 输入你好之后跳转之后的url地址
print(driver.current_url)

cookies = driver.get_cookies()

cookies = {i["name"]: i["value"] for i in cookies}

print(cookies)

# 获取到的是浏览器elements内容，并不是响应的内容
print(driver.page_source)
# driver.quit()