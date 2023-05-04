import React from 'react';
import { render } from 'react-dom';
import { AppContainer } from 'react-hot-loader';
import {Provider} from "react-redux";
import 'core-js';
import store from '../src/redux/store'

import 'element-theme-default';

import './styles/base.scss';
import './styles/prism.css';
import './styles/index.scss';

import App from './page';

render(<Provider store={store}><AppContainer><App /></AppContainer></Provider>, document.getElementById('app'));

if (module.hot) {
  module.hot.accept('./page', () => {
    const App = require('./page').default;
    render(<Provider store={store}><AppContainer><App /></AppContainer></Provider>, document.getElementById('app'));
  });
}
