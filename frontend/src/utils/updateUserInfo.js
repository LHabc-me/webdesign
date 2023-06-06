import {get} from '@/net'
import {useUser} from '@/store/modules/user'

export function updateUserInfo() {
  const user = useUser()
  return get('/api/user/me').then(({data}) => {
    user.id = data.id
    user.name = data.username
    user.email = data.email
    user.type = data.roles ? 'admin' : 'author'
    user.coins = data.coins
    user.hot = data.hot
  })
}
