from bs4 import BeautifulSoup
import requests
import pandas as pd

from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.keys import Keys
from time import sleep
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

#all actions

def scrape(url: str):

    browser = webdriver.Chrome(ChromeDriverManager().install())
    browser.implicitly_wait(10)
    browser.get(url)
    browser.maximize_window()
    # time.sleep(3)

    element = WebDriverWait(browser, 10).until(
            EC.presence_of_element_located((By.XPATH, '//button[@id="onetrust-accept-btn-handler"]'))
        )
    browser.find_elements(By.XPATH, '//button[@id="onetrust-accept-btn-handler"]')[0].click()

    hotels = browser.find_elements(By.CLASS_NAME,'fb01724e5b')
    hotel_page = 1
    passed_hotels = []
    columns = ['Hotel','Liked', 'Disliked', 'Score']
    df = pd.DataFrame(columns=columns)

    while True:
        if hotel_page > 1:
            browser.find_elements(By.CLASS_NAME, 'ce83a38554')[-1].click()
            
            sleep(1.5)
            hotels = browser.find_elements(By.CLASS_NAME,'fb01724e5b')
            
            print(f'hotel_page: {hotel_page}')
        
        if hotel_page > 13:
            break

        
        for ix, hotel in enumerate(hotels):
            
            hotel_name = hotel.text
            hotel_name = hotel_name.split('\n')[0]
            
            if hotel_name in passed_hotels:
                if ix == len(hotels) - 1:
                    hotel_page += 1
                continue
            else:
                passed_hotels.append(hotel_name)
    #         print(f'hotel name: {hotel_name}')
            hotel.click()

            browser.find_element_by_tag_name('body').send_keys(Keys.CONTROL + Keys.TAB)    

            try:
                browser.switch_to.window(browser.window_handles[1])
                sleep(0.5)
            except:
                break
                
    #         print(browser.title)

            if 'No reviews yet' in browser.page_source:
                print('no reviews')
                browser.close()
                browser.switch_to.window(browser.window_handles[0])
                sleep(2)
                continue
                
            try:
                browser.find_elements(by='id', value='show_reviews_tab')[0].click()
            except:
                browser.close()
                browser.switch_to.window(browser.window_handles[0])
                sleep(2)
                continue
            

            element = WebDriverWait(browser, 10).until(
                    EC.presence_of_element_located((By.CLASS_NAME, "c-review"))
                )

            page = 1
            while True:
                if page > 1:
                    try:
                        element = WebDriverWait(browser, 10).until(
                            EC.presence_of_element_located((By.CLASS_NAME, "pagenext"))
                        )
                        browser.find_element_by_class_name('pagenext').click()
                    except:
                        break

                sleep(2)
                try:
                    translation_buttons = browser.find_elements_by_link_text("Show translation")
            #         translation_buttons = browser.find_elements(By.CLASS_NAME,"c-review__translation-link js-review-translation-show-translated")
            #         translation_buttons = browser.find_element_by_class_name("c-review__translation-link js-review-translation-show-translated")
    #                 print('tr buttons:', len(translation_buttons))    
                    for tr_button in translation_buttons:
                            tr_button.click()
                            sleep(0.3)
                except:
                    pass
                sleep(2)
                soup = BeautifulSoup(browser.page_source, 'html.parser')

                scores = soup.find_all("div", {"class": "bui-review-score__badge"})
                scores_nums = [float(score.text.replace(" ", "")) for score in scores]
                comments = soup.find_all("div", {"class": "c-review"})
                final_comments = []
    #             print(f'page: {page}')
                for comment in comments:
                    opinions = comment.findChildren("div" , recursive=False)
                    coms = {}
                    try:
                        try:
                            raw_text =  opinions[0].find_all("span", {"class": "c-review__body c-review__body--translated positive c-review__body--hidden"})[0].text
    #                         print('printing translated liked: ', raw_text)
                            is_liked = opinions[0].findChildren("p" , recursive=False)[0].text[1:6] == 'Liked'
                            if is_liked:
                                coms['Liked'] = raw_text
                            else:
                                coms['Disliked'] = raw_text
                                coms['Liked'] = ''
                        except:
                            try:
                                raw_text =  opinions[0].find_all("span", {"class": "c-review__body c-review__body--translated negative c-review__body--hidden"})[0].text
                                coms['Disliked'] = raw_text
                                coms['Liked'] = ''
                            except:
                                raw_text = opinions[0].findChildren("p" , recursive=False)[0].text
                                if raw_text[1:6] == 'Liked':
                                    coms['Liked'] = raw_text[10:]
                                else:
                                    coms['Liked'] = ''
                                    coms['Disliked'] = raw_text
                    except:
                        coms['Liked'] = ''

                    try:
                        try:
                            raw_text =  opinions[1].find_all("span", {"class": "c-review__body c-review__body--translated negative c-review__body--hidden"})[0].text
    #                         print(f'found translation: {raw_text}')
                            coms['Disliked'] = raw_text
                        except:
                            raw_text = opinions[1].findChildren("p" , recursive=False)[0].text
                            if raw_text[1:9] == 'Disliked':
                                coms['Disliked'] = raw_text[13:]
                            else:
                                coms['Disliked'] = ''
                    except:
                        if not coms.get('Disliked'):
                            coms['Disliked'] = ''

                    final_comments.append(coms)
                
                num_comments = len(final_comments)
                if num_comments > 10:
                    final_comments = final_comments[:len(scores_nums)]
                    
                for i in range(len(final_comments)):
                    df = df.append({'Hotel': hotel_name,
                                    'Liked': final_comments[i].get('Liked',''),
                                    'Disliked': final_comments[i].get('Disliked',''),
                                    'Score': scores_nums[i]
                                }, ignore_index=True)
                page += 1

            browser.close()
            browser.switch_to.window(browser.window_handles[0])
            sleep(2)
        
        hotel_page += 1
    return df

def main(url, output):
    scraped_data = scrape(url)
    scraped_data.to_csv(output)

if __name__ == "__main__":
    import argparse

    parser = argparse.ArgumentParser(description='Scraper')
    parser.add_argument('--url', metavar='path', required=True,
                        help='the url to scrape')
    parser.add_argument('--output', metavar='path', required=True,
                        help='name of out csv file')
    args = parser.parse_args()
    main(args.url, args.output)