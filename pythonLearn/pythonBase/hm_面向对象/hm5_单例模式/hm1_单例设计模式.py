class MusicPlayer(object):
    instance = None
    init_flag = False

    def __new__(cls, *args, **kwargs):
        # 1创建对象时，new方法会被自动调用
        print("创建对象，分配空间")

        # 2为对象分配空间
        if cls.instance is None:
            cls.instance = super().__new__(cls)
        return cls.instance

    def __init__(self):
        if MusicPlayer.init_flag is False:
            print("播放器初始化")
            MusicPlayer.init_flag = True


player = MusicPlayer()
player1 = MusicPlayer()
print(player)
print(player1)