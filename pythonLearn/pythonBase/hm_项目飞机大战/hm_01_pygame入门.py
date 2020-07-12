import pygame
from hm_项目飞机大战.plane_sprites import *

pygame.init()

screen = pygame.display.set_mode((480, 700))
bg = pygame.image.load("./images/background.png")
screen.blit(bg, (0, 0))

hero = pygame.image.load("./images/me1.png")
screen.blit(hero, (150, 300))
# 刷新屏幕显示
pygame.display.update()

clock = pygame.time.Clock()

hero_rect = pygame.Rect(150, 300, 102, 126)

# 定义敌机精灵
enemy = GameSprite("./images/enemy1.png")
enemy1 = GameSprite("./images/enemy1.png", 2)
enemy_group = pygame.sprite.Group(enemy, enemy1)

while True:  # main game loop
    clock.tick(60)

    screen.blit(bg, (0, 0))

    hero_rect.y -= 1
    if hero_rect.y <= 0:
        hero_rect.y = 700
    screen.blit(hero, hero_rect)

    enemy_group.update()
    enemy_group.draw(screen)

    pygame.display.update()
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            exit(0)



