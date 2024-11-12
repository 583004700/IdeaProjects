import request from './request'

request.defaults.withCredentials = true

export default {
	post: (url, params) => {
		return request.post(url, params)
	},

	request: opt => {
		return request(opt)
	}
}
