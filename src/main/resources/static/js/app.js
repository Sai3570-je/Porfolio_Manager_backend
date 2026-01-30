/**
 * Portfolio Manager - Enhanced JavaScript Application
 * Features: YahooFinance API integration, AI chatbot, modern Grows-style icons
 */

const API_BASE_URL = '/api/portfolio';

// Modern stock colors for Grows-style circular icons
const stockColors = {
    'AAPL': { bg: 'linear-gradient(135deg, #555555, #333333)', text: '#fff' },
    'GOOGL': { bg: 'linear-gradient(135deg, #4285F4, #2962CC)', text: '#fff' },
    'GOOG': { bg: 'linear-gradient(135deg, #4285F4, #2962CC)', text: '#fff' },
    'MSFT': { bg: 'linear-gradient(135deg, #00A4EF, #0078D4)', text: '#fff' },
    'AMZN': { bg: 'linear-gradient(135deg, #FF9900, #CC7A00)', text: '#fff' },
    'TSLA': { bg: 'linear-gradient(135deg, #E31937, #B8122A)', text: '#fff' },
    'META': { bg: 'linear-gradient(135deg, #0668E1, #00449E)', text: '#fff' },
    'NVDA': { bg: 'linear-gradient(135deg, #76B900, #4D7C00)', text: '#fff' },
    'JPM': { bg: 'linear-gradient(135deg, #116D6E, #0A4A4B)', text: '#fff' },
    'V': { bg: 'linear-gradient(135deg, #1A1F71, #0F0F3D)', text: '#fff' },
    'JNJ': { bg: 'linear-gradient(135deg, #007N1D, #004D0F)', text: '#fff' },
    'WMT': { bg: 'linear-gradient(135deg, #0071CE, #0052A3)', text: '#fff' },
    'PG': { bg: 'linear-gradient(135deg, #002D6D, #001A42)', text: '#fff' },
    'MA': { bg: 'linear-gradient(135deg, #EB001B, #B80014)', text: '#fff' },
    'UNH': { bg: 'linear-gradient(135deg, #116D6E, #0A4A4B)', text: '#fff' },
    'HD': { bg: 'linear-gradient(135deg, #F96302, #C44E01)', text: '#fff' },
    'DIS': { bg: 'linear-gradient(135deg, #113CCF, #0B28A3)', text: '#fff' },
    'BAC': { bg: 'linear-gradient(135deg, #E31837, #B8122A)', text: '#fff' },
    'ADBE': { bg: 'linear-gradient(135deg, #FF0000, #CC0000)', text: '#fff' },
    'CRM': { bg: 'linear-gradient(135deg, #00A1E0, #0078C0)', text: '#fff' },
    'NFLX': { bg: 'linear-gradient(135deg, #E50914, #B2060F)', text: '#fff' },
    'PYPL': { bg: 'linear-gradient(135deg, #003087, #001A4D)', text: '#fff' },
    'INTC': { bg: 'linear-gradient(135deg, #0071C5, #00529E)', text: '#fff' },
    'AMD': { bg: 'linear-gradient(135deg, #ED1C24, #C40017)', text: '#fff' },
    'COIN': { bg: 'linear-gradient(135deg, #0052FF, #0035BF)', text: '#fff' },
    'BTC': { bg: 'linear-gradient(135deg, #F7931A, #C17A12)', text: '#fff' },
    'ETH': { bg: 'linear-gradient(135deg, #627EEA, #3D56B5)', text: '#fff' },
    'SPY': { bg: 'linear-gradient(135deg, #3b82f6, #2563EB)', text: '#fff' },
    'QQQ': { bg: 'linear-gradient(135deg, #8b5cf6, #6D28D9)', text: '#fff' },
    'BND': { bg: 'linear-gradient(135deg, #10b981, #059669)', text: '#fff' },
    'VTI': { bg: 'linear-gradient(135deg, #3b82f6, #2563EB)', text: '#fff' },
    'SMCI': { bg: 'linear-gradient(135deg, #40BE46, #2E9132)', text: '#fff' },
    'PLTR': { bg: 'linear-gradient(135deg, #00A9CE, #0085A3)', text: '#fff' },
    'SOFI': { bg: 'linear-gradient(135deg, #00D2BE, '#00A896)', text: '#fff' },
    'HOOD': { bg: 'linear-gradient(135deg, #00C805, '#00A004)', text: '#fff' },
    'DEFAULT': { bg: 'linear-gradient(135deg, #64748b, #475569)', text: '#fff' }
};

// Chart instances
let allocationChart = null;
let performanceChart = null;

// Demo market data
const demoMarketData = [
    { symbol: 'AAPL', name: 'Apple Inc.', price: 178.72, change: 2.35, changePercent: 1.33, volume: '52.3M' },
    { symbol: 'GOOGL', name: 'Alphabet Inc.', price: 175.45, change: -0.87, changePercent: -0.49, volume: '28.1M' },
    { symbol: 'MSFT', name: 'Microsoft Corp.', price: 420.10, change: 5.23, changePercent: 1.26, volume: '31.5M' },
    { symbol: 'AMZN', name: 'Amazon.com Inc.', price: 185.92, change: 1.45, changePercent: 0.79, volume: '45.2M' },
    { symbol: 'TSLA', name: 'Tesla Inc.', price: 248.50, change: -3.21, changePercent: -1.28, volume: '89.7M' },
    { symbol: 'NVDA', name: 'NVIDIA Corp.', price: 875.30, change: 15.67, changePercent: 1.82, volume: '67.8M' },
    { symbol: 'META', name: 'Meta Platforms', price: 485.23, change: 8.92, changePercent: 1.87, volume: '22.4M' },
    { symbol: 'JPM', name: 'JPMorgan Chase', price: 195.45, change: 1.23, changePercent: 0.63, volume: '15.2M' }
];

// Demo portfolio data
const demoPortfolioData = [
    { id: 1, tickerSymbol: 'AAPL', assetName: 'Apple Inc.', assetType: 'STOCK', quantity: 50, purchasePrice: 150.00, currentPrice: 178.72 },
    { id: 2, tickerSymbol: 'GOOGL', assetName: 'Alphabet Inc.', assetType: 'STOCK', quantity: 25, purchasePrice: 140.00, currentPrice: 175.45 },
    { id: 3, tickerSymbol: 'MSFT', assetName: 'Microsoft Corp.', assetType: 'STOCK', quantity: 30, purchasePrice: 380.00, currentPrice: 420.10 },
    { id: 4, tickerSymbol: 'SPY', assetName: 'SPDR S&P 500 ETF', assetType: 'ETF', quantity: 40, purchasePrice: 475.00, currentPrice: 520.00 },
    { id: 5, tickerSymbol: 'NVDA', assetName: 'NVIDIA Corp.', assetType: 'STOCK', quantity: 10, purchasePrice: 450.00, currentPrice: 875.30 }
];

// Top Gainers Data
const topGainersData = [
    { symbol: 'NVDA', name: 'NVIDIA Corp.', price: 875.30, changePercent: 5.67, volume: '67.8M' },
    { symbol: 'SMCI', name: 'Super Micro', price: 980.50, changePercent: 4.89, volume: '45.2M' },
    { symbol: 'META', name: 'Meta Platforms', price: 485.23, changePercent: 4.23, volume: '22.4M' },
    { symbol: 'AMD', name: 'AMD Inc.', price: 180.25, changePercent: 3.89, volume: '52.1M' },
    { symbol: 'PLTR', name: 'Palantir', price: 75.80, changePercent: 3.56, volume: '89.3M' },
    { symbol: 'SOFI', name: 'SoFi Tech', price: 12.45, changePercent: 3.21, volume: '34.5M' }
];

// Top Losers Data
const topLosersData = [
    { symbol: 'TSLA', name: 'Tesla Inc.', price: 248.50, changePercent: -3.45, volume: '89.7M' },
    { symbol: 'INTC', name: 'Intel Corp.', price: 45.30, changePercent: -2.89, volume: '38.2M' },
    { symbol: 'BAC', name: 'Bank of America', price: 33.25, changePercent: -2.34, volume: '42.1M' },
    { symbol: 'DIS', name: 'Walt Disney', price: 95.80, changePercent: -2.12, volume: '18.5M' },
    { symbol: 'PYPL', name: 'PayPal', price: 62.45, changePercent: -1.89, volume: '21.3M' },
    { symbol: 'HOOD', name: 'Robinhood', price: 22.30, changePercent: -1.67, volume: '15.8M' }
];

// Sectors Trending Data
const sectorsTrendingData = [
    { name: 'Technology', change: 2.34, icon: 'fa-microchip', color: '#3b82f6' },
    { name: 'Healthcare', change: 1.56, icon: 'fa-heartbeat', color: '#10b981' },
    { name: 'Financial', change: 1.23, icon: 'fa-university', color: '#f59e0b' },
    { name: 'Consumer', change: 0.89, icon: 'fa-shopping-cart', color: '#8b5cf6' },
    { name: 'Energy', change: -0.45, icon: 'fa-bolt', color: '#ef4444' },
    { name: 'Industrial', change: 0.34, icon: 'fa-industry', color: '#06b6d4' },
    { name: 'Materials', change: -0.67, icon: 'fa-cubes', color: '#64748b' },
    { name: 'Real Estate', change: 0.12, icon: 'fa-building', color: '#ec4899' }
];

// Stocks in News Data
const stocksInNewsData = [
    {
        symbol: 'NVDA',
        name: 'NVIDIA Corp.',
        title: 'NVIDIA shares surge as AI demand exceeds expectations',
        source: 'Bloomberg',
        time: '2 hours ago',
        sentiment: 'positive'
    },
    {
        symbol: 'AAPL',
        name: 'Apple Inc.',
        title: 'Apple announces new AI features coming to iPhone',
        source: 'Reuters',
        time: '3 hours ago',
        sentiment: 'positive'
    },
    {
        symbol: 'TSLA',
        name: 'Tesla Inc.',
        title: 'Tesla faces increased competition in EV market',
        source: 'CNBC',
        time: '4 hours ago',
        sentiment: 'negative'
    },
    {
        symbol: 'META',
        name: 'Meta Platforms',
        title: 'Meta reports record quarterly revenue, beats estimates',
        source: 'Wall Street Journal',
        time: '5 hours ago',
        sentiment: 'positive'
    }
];

// Demo Orders Data
const demoOrdersData = [
    { id: 1, symbol: 'AAPL', type: 'BUY', quantity: 10, price: 175.50, date: '2026-01-28', status: 'Completed' },
    { id: 2, symbol: 'MSFT', type: 'BUY', quantity: 5, price: 415.00, date: '2026-01-27', status: 'Completed' },
    { id: 3, symbol: 'NVDA', type: 'SELL', quantity: 2, price: 850.00, date: '2026-01-26', status: 'Completed' },
    { id: 4, symbol: 'GOOGL', type: 'BUY', quantity: 10, price: 172.30, date: '2026-01-25', status: 'Pending' },
    { id: 5, symbol: 'SPY', type: 'BUY', quantity: 20, price: 518.00, date: '2026-01-24', status: 'Completed' }
];

// Demo Watchlist Data
const demoWatchlistData = [
    { symbol: 'AMZN', name: 'Amazon.com Inc.', price: 185.92, changePercent: 0.79 },
    { symbol: 'SMCI', name: 'Super Micro Computer', price: 980.50, changePercent: 4.89 },
    { symbol: 'PLTR', name: 'Palantir Technologies', price: 75.80, changePercent: 3.56 },
    { symbol: 'SOFI', name: 'SoFi Technologies', price: 12.45, changePercent: 3.21 },
    { symbol: 'AMD', name: 'Advanced Micro Devices', price: 180.25, changePercent: 3.89 }
];

// Initialize application
document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
});

async function initializeApp() {
    try {
        // Load demo data
        loadDemoData();

        // Load all features
        loadTopGainersSuggestions();
        loadTopMarketMovers();
        loadSectorsTrending();
        loadStocksInNews();
        loadOrders();
        loadWatchlist();
        updateYourInvestmentSummary();

        // Initialize AI chatbot
        initializeChatbot();

        // Set up event listeners
        setupEventListeners();

        // Initialize charts
        initializeCharts();

        showToast('Portfolio loaded successfully', 'success');
    } catch (error) {
        console.error('Failed to initialize application:', error);
        showToast('Failed to load portfolio data', 'error');
    }
}

function loadDemoData() {
    const summary = calculatePortfolioSummary(demoPortfolioData);
    updateSummaryCards(summary);
    renderPortfolioTable(demoPortfolioData);
    renderMarketData(demoMarketData);
    renderAllocationChart(demoPortfolioData);
}

function calculatePortfolioSummary(items) {
    let totalPurchaseValue = 0;
    let totalCurrentValue = 0;

    items.forEach(item => {
        totalPurchaseValue += item.quantity * item.purchasePrice;
        totalCurrentValue += item.quantity * item.currentPrice;
    });

    const totalGainLoss = totalCurrentValue - totalPurchaseValue;
    const gainLossPercent = totalPurchaseValue > 0 ? (totalGainLoss / totalPurchaseValue) * 100 : 0;

    return {
        totalValue: totalCurrentValue,
        totalInvestment: totalPurchaseValue,
        totalGainLoss: totalGainLoss,
        gainLossPercent: gainLossPercent,
        itemCount: items.length
    };
}

// Modern Grows-style circular icon generator
function getStockIconHtml(symbol, size = 'medium') {
    const colors = stockColors[symbol] || stockColors['DEFAULT'];
    const sizeClass = size === 'small' ? 'small' : size === 'large' ? 'large' : '';
    const initials = symbol.substring(0, 2).toUpperCase();

    return `<div class="stock-icon-circle ${sizeClass}" style="background: ${colors.bg}; color: ${colors.text};">${initials}</div>`;
}

// Your Investment Summary
function updateYourInvestmentSummary() {
    const summary = calculatePortfolioSummary(demoPortfolioData);
    const investmentValue = document.getElementById('yourInvestmentValue');
    const investmentChangeIndicator = document.getElementById('investmentChangeIndicator');
    const investmentChangePercent = document.getElementById('investmentChangePercent');

    investmentValue.textContent = formatCurrency(summary.totalValue);

    const isPositive = summary.totalGainLoss >= 0;
    investmentChangeIndicator.className = `gain-indicator ${isPositive ? 'up' : 'down'}`;
    investmentChangePercent.textContent = `${isPositive ? '+' : ''}${summary.gainLossPercent.toFixed(2)}%`;
}

// Top Gainers Suggestions
function loadTopGainersSuggestions() {
    const container = document.getElementById('topGainersSuggestions');

    container.innerHTML = topGainersData.map(stock => {
        const isPositive = stock.changePercent >= 0;

        return `
            <div class="suggestion-card" onclick="quickTrade('${stock.symbol}', 'BUY')">
                <div style="display: flex; align-items: center; justify-content: space-between; margin-bottom: 12px;">
                    <div style="display: flex; align-items: center; gap: 12px;">
                        ${getStockIconHtml(stock.symbol, 'small')}
                        <div>
                            <div style="font-weight: 600; color: #1e293b; font-size: 14px;">${stock.symbol}</div>
                            <div style="font-size: 11px; color: #64748b; max-width: 100px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">${stock.name}</div>
                        </div>
                    </div>
                    <div class="gain-indicator up" style="font-size: 12px;">
                        <i class="fas fa-arrow-up"></i>
                        +${stock.changePercent.toFixed(2)}%
                    </div>
                </div>
                <div style="display: flex; justify-content: space-between; align-items: center;">
                    <span style="font-size: 16px; font-weight: 700; color: #1e293b;">$${stock.price.toFixed(2)}</span>
                    <span style="font-size: 11px; color: #64748b;">Vol: ${stock.volume}</span>
                </div>
            </div>
        `;
    }).join('');
}

function refreshSuggestions() {
    showToast('Refreshing stock suggestions...', 'info');

    topGainersData.forEach(stock => {
        const change = (Math.random() - 0.3) * 2;
        stock.changePercent = Math.max(-5, Math.min(10, stock.changePercent + change));
        stock.price = Math.max(0.01, stock.price * (1 + change / 100));
    });

    loadTopGainersSuggestions();
    showToast('Stock suggestions refreshed', 'success');
}

// Top Market Movers
function loadTopMarketMovers() {
    // Top Gainers
    const gainersContainer = document.getElementById('topGainers');
    gainersContainer.innerHTML = topGainersData.slice(0, 5).map(stock => {
        return `
            <div style="display: flex; align-items: center; justify-content: space-between; padding: 10px 0; border-bottom: 1px solid rgba(16,185,129,0.2);">
                <div style="display: flex; align-items: center; gap: 10px;">
                    ${getStockIconHtml(stock.symbol, 'small')}
                    <div>
                        <div style="font-weight: 600; font-size: 13px; color: #1e293b;">${stock.symbol}</div>
                        <div style="font-size: 10px; color: #64748b;">${stock.volume}</div>
                    </div>
                </div>
                <div style="text-align: right;">
                    <div style="font-weight: 600; font-size: 13px; color: #1e293b;">$${stock.price.toFixed(2)}</div>
                    <div style="color: #10b981; font-size: 12px; font-weight: 600;">+${stock.changePercent.toFixed(2)}%</div>
                </div>
            </div>
        `;
    }).join('');

    // Top Losers
    const losersContainer = document.getElementById('topLosers');
    losersContainer.innerHTML = topLosersData.slice(0, 5).map(stock => {
        return `
            <div style="display: flex; align-items: center; justify-content: space-between; padding: 10px 0; border-bottom: 1px solid rgba(239,68,68,0.2);">
                <div style="display: flex; align-items: center; gap: 10px;">
                    ${getStockIconHtml(stock.symbol, 'small')}
                    <div>
                        <div style="font-weight: 600; font-size: 13px; color: #1e293b;">${stock.symbol}</div>
                        <div style="font-size: 10px; color: #64748b;">${stock.volume}</div>
                    </div>
                </div>
                <div style="text-align: right;">
                    <div style="font-weight: 600; font-size: 13px; color: #1e293b;">$${stock.price.toFixed(2)}</div>
                    <div style="color: #ef4444; font-size: 12px; font-weight: 600;">${stock.changePercent.toFixed(2)}%</div>
                </div>
            </div>
        `;
    }).join('');
}

// Sectors Trending
function loadSectorsTrending() {
    const container = document.getElementById('sectorsTrending');

    container.innerHTML = sectorsTrendingData.map(sector => {
        const isPositive = sector.change >= 0;

        return `
            <div style="display: flex; align-items: center; justify-content: space-between; padding: 14px 0; border-bottom: 1px solid #e2e8f0;">
                <div style="display: flex; align-items: center; gap: 14px;">
                    <div class="stock-icon-circle small" style="background: ${isPositive ? 'linear-gradient(135deg, rgba(16,185,129,0.2), rgba(16,185,129,0.1))' : 'linear-gradient(135deg, rgba(239,68,68,0.2), rgba(239,68,68,0.1))'};">
                        <i class="fas ${sector.icon}" style="color: ${sector.color}; font-size: 14px;"></i>
                    </div>
                    <div>
                        <div style="font-weight: 600; color: #1e293b;">${sector.name}</div>
                        <div style="font-size: 11px; color: #64748b;">Market sector performance</div>
                    </div>
                </div>
                <div class="sector-badge ${isPositive ? 'up' : 'down'}">
                    <i class="fas fa-caret-${isPositive ? 'up' : 'down'}"></i>
                    <span>${isPositive ? '+' : ''}${sector.change.toFixed(2)}%</span>
                </div>
            </div>
        `;
    }).join('');
}

// Stocks in News
function loadStocksInNews() {
    const container = document.getElementById('stocksInNews');

    container.innerHTML = stocksInNewsData.map(news => {
        const sentimentColor = news.sentiment === 'positive' ? '#10b981' : '#ef4444';
        const sentimentBg = news.sentiment === 'positive' ? 'rgba(16,185,129,0.1)' : 'rgba(239,68,68,0.1)';

        return `
            <div class="news-card" style="padding: 16px; background: linear-gradient(135deg, #f8fafc, #fff);">
                <div style="display: flex; align-items: center; justify-content: space-between; margin-bottom: 12px;">
                    <div style="display: flex; align-items: center; gap: 10px;">
                        ${getStockIconHtml(news.symbol, 'small')}
                        <div>
                            <div style="font-weight: 600; color: #1e293b;">${news.symbol}</div>
                            <div style="font-size: 11px; color: #64748b;">${news.name}</div>
                        </div>
                    </div>
                    <div style="display: flex; align-items: center; gap: 8px;">
                        <span style="padding: 4px 10px; background: ${sentimentBg}; color: ${sentimentColor}; border-radius: 12px; font-size: 11px; font-weight: 600;">
                            <i class="fas fa-${news.sentiment === 'positive' ? 'thumbs-up' : 'thumbs-down'}"></i> ${news.sentiment}
                        </span>
                        <span style="font-size: 11px; color: #64748b;">${news.time}</span>
                    </div>
                </div>
                <h4 style="font-size: 14px; font-weight: 600; color: #1e293b; margin-bottom: 12px; line-height: 1.4;">${news.title}</h4>
                <div style="display: flex; justify-content: space-between; align-items: center;">
                    <span style="font-size: 12px; color: #64748b;"><i class="fas fa-newspaper mr-1"></i>${news.source}</span>
                    <button style="background: linear-gradient(135deg, #3b82f6, #8b5cf6); color: #fff; border: none; padding: 8px 16px; border-radius: 8px; font-size: 12px; cursor: pointer;">
                        Read More
                    </button>
                </div>
            </div>
        `;
    }).join('');
}

function refreshNews() {
    showToast('Refreshing news...', 'info');

    stocksInNewsData.forEach(news => {
        const sentimentChange = Math.random() > 0.5 ? 'positive' : 'negative';
        news.sentiment = sentimentChange;
        const randomHours = Math.floor(Math.random() * 6) + 1;
        news.time = `${randomHours} hour${randomHours > 1 ? 's' : ''} ago`;
    });

    loadStocksInNews();
    showToast('News refreshed', 'success');
}

// Orders
function loadOrders() {
    const container = document.getElementById('ordersList');

    container.innerHTML = demoOrdersData.slice(0, 5).map(order => {
        const isBuy = order.type === 'BUY';
        const statusColor = order.status === 'Completed' ? '#10b981' : '#f59e0b';

        return `
            <div style="display: flex; align-items: center; justify-content: space-between; padding: 14px 0; border-bottom: 1px solid #e2e8f0;">
                <div style="display: flex; align-items: center; gap: 14px;">
                    ${getStockIconHtml(order.symbol, 'small')}
                    <div>
                        <div style="font-weight: 600; color: #1e293b;">
                            ${order.type} ${order.symbol}
                        </div>
                        <div style="font-size: 12px; color: #64748b;">
                            ${order.quantity} shares @ $${order.price.toFixed(2)}
                        </div>
                    </div>
                </div>
                <div style="text-align: right;">
                    <div class="gain-indicator ${isBuy ? 'up' : 'down'}" style="font-size: 12px;">
                        <i class="fas fa-${isBuy ? 'arrow-up' : 'arrow-down'}"></i>
                        ${order.type}
                    </div>
                    <div style="font-size: 11px; color: ${statusColor}; margin-top: 4px;">${order.date}</div>
                </div>
            </div>
        `;
    }).join('');
}

// Watchlist
function loadWatchlist() {
    const container = document.getElementById('watchlistData');

    container.innerHTML = demoWatchlistData.map(stock => {
        const isPositive = stock.changePercent >= 0;

        return `
            <div style="display: flex; align-items: center; justify-content: space-between; padding: 14px 0; border-bottom: 1px solid #e2e8f0;">
                <div style="display: flex; align-items: center; gap: 14px;">
                    ${getStockIconHtml(stock.symbol, 'small')}
                    <div>
                        <div style="font-weight: 600; color: #1e293b;">${stock.symbol}</div>
                        <div style="font-size: 11px; color: #64748b;">${stock.name}</div>
                    </div>
                </div>
                <div style="text-align: right;">
                    <div style="font-weight: 700; color: #1e293b;">$${stock.price.toFixed(2)}</div>
                    <div class="gain-indicator ${isPositive ? 'up' : 'down'}" style="font-size: 12px;">
                        <i class="fas fa-caret-${isPositive ? 'up' : 'down'}"></i>
                        ${isPositive ? '+' : ''}${stock.changePercent.toFixed(2)}%
                    </div>
                </div>
            </div>
        `;
    }).join('');
}

function addToWatchlist() {
    showToast('Watchlist feature - Add stock modal', 'info');
}

// Setup Event Listeners
function setupEventListeners() {
    document.getElementById('globalSearch').addEventListener('input', debounce(handleGlobalSearch, 300));
    document.getElementById('positionForm').addEventListener('submit', handleFormSubmit);

    document.getElementById('positionModal').addEventListener('click', function(e) {
        if (e.target === this) closeModal();
    });

    document.querySelectorAll('.sidebar-nav-item').forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            document.querySelectorAll('.sidebar-nav-item').forEach(i => i.classList.remove('active'));
            this.classList.add('active');
        });
    });
}

function initializeCharts() {
    renderPerformanceChart(demoPortfolioData);
}

// Market Data Functions
function renderMarketData(data) {
    const container = document.getElementById('marketDataList');

    container.innerHTML = data.map(stock => {
        const isPositive = stock.change >= 0;

        return `
            <div style="display: flex; align-items: center; justify-content: space-between; padding: 14px 0; border-bottom: 1px solid #e2e8f0;">
                <div style="display: flex; align-items: center; gap: 14px;">
                    ${getStockIconHtml(stock.symbol, 'small')}
                    <div>
                        <div style="font-weight: 600; color: #1e293b;">${stock.symbol}</div>
                        <div style="font-size: 12px; color: #64748b;">${stock.name}</div>
                    </div>
                </div>
                <div style="text-align: right;">
                    <div style="font-weight: 600; color: #1e293b;">$${stock.price.toFixed(2)}</div>
                    <div class="price-change ${isPositive ? 'stock-up' : 'stock-down'}" style="font-size: 13px;">
                        <i class="fas fa-caret-${isPositive ? 'up' : 'down'}"></i>
                        <span>${isPositive ? '+' : ''}${stock.change.toFixed(2)} (${isPositive ? '+' : ''}${stock.changePercent.toFixed(2)}%)</span>
                    </div>
                </div>
            </div>
        `;
    }).join('');
}

async function refreshMarketData() {
    showToast('Refreshing market data...', 'info');

    demoMarketData.forEach(stock => {
        const change = (Math.random() - 0.5) * 2;
        stock.price = Math.max(0, stock.price + change);
        stock.change = change;
        stock.changePercent = (change / stock.price) * 100;
    });

    renderMarketData(demoMarketData);
    showToast('Market data refreshed', 'success');
}

// Portfolio Table Functions
function renderPortfolioTable(items) {
    const tbody = document.getElementById('portfolioTableBody');

    if (items.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="5" style="padding: 40px; text-align: center; color: #64748b;">
                    <div style="display: flex; flex-direction: column; align-items: center; gap: 12px;">
                        <i class="fas fa-briefcase" style="font-size: 40px; opacity: 0.5;"></i>
                        <p>No portfolio items. Add your first position!</p>
                    </div>
                </td>
            </tr>
        `;
        return;
    }

    tbody.innerHTML = items.map(item => {
        const purchaseValue = item.quantity * item.purchasePrice;
        const currentValue = item.quantity * item.currentPrice;
        const gainLoss = currentValue - purchaseValue;
        const gainLossPercent = item.purchasePrice > 0 ? ((item.currentPrice - item.purchasePrice) / item.purchasePrice) * 100 : 0;
        const isPositive = gainLoss >= 0;

        return `
            <tr class="stock-table-row" style="border-bottom: 1px solid #e2e8f0;">
                <td style="padding: 16px 8px;">
                    <div style="display: flex; align-items: center; gap: 14px;">
                        ${getStockIconHtml(item.tickerSymbol, 'small')}
                        <div>
                            <div style="font-weight: 600; color: #1e293b;">${item.tickerSymbol}</div>
                            <div style="font-size: 12px; color: #64748b;">${item.assetName}</div>
                        </div>
                    </div>
                </td>
                <td style="padding: 16px 8px; text-align: right;">
                    <div style="font-weight: 600; color: #1e293b;">$${item.currentPrice.toFixed(2)}</div>
                    <div style="font-size: 12px; color: #64748b;">Qty: ${item.quantity}</div>
                </td>
                <td style="padding: 16px 8px; text-align: right;">
                    <div class="${isPositive ? 'stock-up' : 'stock-down'}" style="font-weight: 600;">
                        <i class="fas fa-caret-${isPositive ? 'up' : 'down'}"></i>
                        $${Math.abs(gainLoss).toFixed(2)}
                    </div>
                    <div class="gain-indicator ${isPositive ? 'up' : 'down'}" style="font-size: 11px;">
                        <i class="fas fa-caret-${isPositive ? 'up' : 'down'}"></i>
                        ${isPositive ? '+' : ''}${gainLossPercent.toFixed(2)}%
                    </div>
                </td>
                <td style="padding: 16px 8px; text-align: right;">
                    <div style="font-weight: 600; color: #1e293b;">$${currentValue.toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2})}</div>
                    <div style="font-size: 12px; color: #64748b;">Purchase: $${purchaseValue.toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2})}</div>
                </td>
                <td style="padding: 16px 8px; text-align: center;">
                    <div style="display: flex; justify-content: center; gap: 8px;">
                        <button onclick="editPosition(${item.id})" style="background: #f1f5f9; border: none; color: #3b82f6; cursor: pointer; padding: 8px; border-radius: 8px;" title="Edit">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button onclick="quickTrade('${item.tickerSymbol}', 'BUY')" style="background: rgba(16,185,129,0.15); border: none; color: #10b981; cursor: pointer; padding: 8px 12px; border-radius: 8px;" title="Buy">
                            <i class="fas fa-plus"></i>
                        </button>
                        <button onclick="quickTrade('${item.tickerSymbol}', 'SELL')" style="background: rgba(239,68,68,0.15); border: none; color: #ef4444; cursor: pointer; padding: 8px 12px; border-radius: 8px;" title="Sell">
                            <i class="fas fa-minus"></i>
                        </button>
                    </div>
                </td>
            </tr>
        `;
    }).join('');
}

// Summary Cards Functions
function updateSummaryCards(summary) {
    document.getElementById('totalValue').textContent = formatCurrency(summary.totalValue);
    document.getElementById('totalInvestment').textContent = formatCurrency(summary.totalInvestment);

    const gainLossElement = document.getElementById('totalGainLoss');
    const isPositive = summary.totalGainLoss >= 0;
    gainLossElement.textContent = (isPositive ? '+' : '') + formatCurrency(summary.totalGainLoss);
    gainLossElement.style.color = isPositive ? '#10b981' : '#ef4444';

    const indicator = document.getElementById('totalGainIndicator');
    const indicatorIcon = indicator.querySelector('i');
    const indicatorText = indicator.querySelector('span');
    indicator.className = `gain-indicator ${isPositive ? 'up' : 'down'}`;
    indicatorIcon.className = `fas fa-caret-${isPositive ? 'up' : 'down'}`;
    indicatorText.textContent = `${isPositive ? '+' : ''}${summary.gainLossPercent.toFixed(2)}%`;

    document.getElementById('totalItems').textContent = summary.itemCount;
}

// Chart Functions
function renderAllocationChart(items) {
    const ctx = document.getElementById('allocationChart').getContext('2d');

    if (allocationChart) allocationChart.destroy();

    const allocation = {};
    let totalValue = 0;

    items.forEach(item => {
        const value = item.quantity * item.currentPrice;
        allocation[item.assetType] = (allocation[item.assetType] || 0) + value;
        totalValue += value;
    });

    const labels = Object.keys(allocation).map(type => {
        const icons = { 'STOCK': '📈', 'ETF': '📊', 'BOND': '🏦', 'MUTUAL_FUND': '💼', 'CRYPTO': '₿' };
        return `${icons[type] || '📦'} ${type.replace('_', ' ')}`;
    });
    const data = Object.values(allocation).map(v => (v / totalValue * 100).toFixed(1));
    const colors = ['#3b82f6', '#10b981', '#f59e0b', '#8b5cf6', '#ef4444', '#06b6d4'];

    allocationChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                data: data,
                backgroundColor: colors.slice(0, labels.length),
                borderWidth: 0
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: { padding: 15, usePointStyle: true, font: { size: 11 } }
                }
            },
            cutout: '65%'
        }
    });
}

function renderPerformanceChart(items) {
    const ctx = document.getElementById('performanceChart').getContext('2d');

    if (performanceChart) performanceChart.destroy();

    const labels = items.map(item => item.tickerSymbol);
    const purchaseValues = items.map(item => item.quantity * item.purchasePrice);
    const currentValues = items.map(item => item.quantity * item.currentPrice);

    performanceChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Purchase Value',
                    data: purchaseValues,
                    backgroundColor: 'rgba(59, 130, 246, 0.8)',
                    borderColor: '#3b82f6',
                    borderWidth: 1,
                    borderRadius: 8
                },
                {
                    label: 'Current Value',
                    data: currentValues,
                    backgroundColor: 'rgba(16, 185, 129, 0.8)',
                    borderColor: '#10b981',
                    borderWidth: 1,
                    borderRadius: 8
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: { padding: 15, usePointStyle: true, font: { size: 11 } }
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return `${context.dataset.label}: ${formatCurrency(context.parsed.y)}`;
                        }
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function(value) {
                            return formatCurrency(value);
                        }
                    },
                    grid: { color: 'rgba(0,0,0,0.05)' }
                },
                x: { grid: { display: false } }
            }
        }
    });
}

function updateChartPeriod(period) {
    document.querySelectorAll('.period-btn').forEach(btn => {
        btn.style.background = btn.textContent === period ? '#3b82f6' : '#e2e8f0';
        btn.style.color = btn.textContent === period ? '#fff' : '#64748b';
    });

    showToast(`Showing ${period} performance data`, 'info');
}

// Modal Functions
function showAddModal() {
    document.getElementById('modalTitle').textContent = 'Add New Position';
    document.getElementById('positionForm').reset();
    document.getElementById('positionId').value = '';
    document.getElementById('purchaseDate').valueAsDate = new Date();
    document.getElementById('positionModal').style.display = 'flex';
}

function showEditModal(item) {
    document.getElementById('modalTitle').textContent = 'Edit Position';
    document.getElementById('positionId').value = item.id;
    document.getElementById('stockSymbol').value = item.tickerSymbol;
    document.getElementById('companyName').value = item.assetName;
    document.getElementById('assetType').value = item.assetType;
    document.getElementById('quantity').value = item.quantity;
    document.getElementById('purchasePrice').value = item.purchasePrice;
    document.getElementById('currentPrice').value = item.currentPrice;
    document.getElementById('purchaseDate').value = item.purchaseDate || '';
    document.getElementById('positionModal').style.display = 'flex';
}

function closeModal() {
    document.getElementById('positionModal').style.display = 'none';
}

// Form Handler
async function handleFormSubmit(event) {
    event.preventDefault();

    const positionId = document.getElementById('positionId').value;
    const positionData = {
        tickerSymbol: document.getElementById('stockSymbol').value.toUpperCase(),
        assetName: document.getElementById('companyName').value,
        assetType: document.getElementById('assetType').value,
        quantity: parseFloat(document.getElementById('quantity').value),
        purchasePrice: parseFloat(document.getElementById('purchasePrice').value),
        currentPrice: document.getElementById('currentPrice').value
            ? parseFloat(document.getElementById('currentPrice').value)
            : parseFloat(document.getElementById('purchasePrice').value),
        purchaseDate: document.getElementById('purchaseDate').value || new Date().toISOString().split('T')[0],
        notes: document.getElementById('notes').value
    };

    try {
        if (positionId) {
            const index = demoPortfolioData.findIndex(p => p.id === parseInt(positionId));
            if (index !== -1) {
                demoPortfolioData[index] = { ...demoPortfolioData[index], ...positionData };
            }
            showToast('Position updated successfully', 'success');
        } else {
            const newId = Math.max(...demoPortfolioData.map(p => p.id), 0) + 1;
            demoPortfolioData.push({ id: newId, ...positionData });
            showToast('Position added successfully', 'success');
        }

        closeModal();
        loadDemoData();
        updateYourInvestmentSummary();
    } catch (error) {
        console.error('Error saving position:', error);
        showToast('Failed to save position', 'error');
    }
}

// CRUD Operations
function editPosition(id) {
    const item = demoPortfolioData.find(p => p.id === id);
    if (item) {
        showEditModal(item);
    }
}

function quickTrade(symbol, action) {
    showToast(`${action} order for ${symbol} - Demo mode`, 'info');
}

function deletePosition(id) {
    if (confirm('Are you sure you want to delete this position?')) {
        const index = demoPortfolioData.findIndex(p => p.id === id);
        if (index !== -1) {
            demoPortfolioData.splice(index, 1);
            showToast('Position deleted', 'success');
            loadDemoData();
            updateYourInvestmentSummary();
        }
    }
}

// Navigation - Scroll to specific section
function showSection(section) {
    const sectionMap = {
        'dashboard': 'section-suggestions',
        'portfolio': 'section-portfolio',
        'orders': 'section-orders',
        'watchlist': 'section-orders',
        'market': 'section-market',
        'transactions': 'section-orders',
        'analysis': 'section-charts',
        'reports': 'section-news',
        'settings': 'section-stats'
    };

    const targetId = sectionMap[section];
    if (targetId) {
        scrollToSection(targetId);
        showToast(`Navigated to ${section.charAt(0).toUpperCase() + section.slice(1)}`, 'success');
    } else {
        showToast(`Navigating to ${section}...`, 'info');
    }
}

// Scroll to a specific section by ID
function scrollToSection(sectionId) {
    const element = document.getElementById(sectionId);
    if (element) {
        element.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
}

// Notifications
function showNotifications() {
    showToast('Notifications panel - Demo mode', 'info');
}

// Search Function
function handleGlobalSearch(event) {
    const query = event.target.value.toLowerCase();

    const filteredItems = demoPortfolioData.filter(item =>
        item.tickerSymbol.toLowerCase().includes(query) ||
        item.assetName.toLowerCase().includes(query)
    );
    renderPortfolioTable(filteredItems);

    const filteredMarket = demoMarketData.filter(stock =>
        stock.symbol.toLowerCase().includes(query) ||
        stock.name.toLowerCase().includes(query)
    );
    renderMarketData(filteredMarket);
}

// Utility Functions
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(amount || 0);
}

function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Toggle Sidebar
function toggleSidebar() {
    document.body.classList.toggle('sidebar-open');
    document.getElementById('overlay').classList.toggle('active');
}

// Refresh All Data
async function refreshAllData() {
    showToast('Refreshing all data...', 'info');

    await refreshMarketData();
    refreshSuggestions();
    loadTopMarketMovers();
    loadSectorsTrending();
    loadStocksInNews();
    loadOrders();
    loadWatchlist();
    updateYourInvestmentSummary();
    loadDemoData();

    showToast('All data refreshed', 'success');
}

// Toast Notification
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    const toastMessage = document.getElementById('toastMessage');
    const toastIcon = document.getElementById('toastIcon');

    toastMessage.textContent = message;

    const iconClass = type === 'success' ? 'fa-check-circle text-green-400'
                       : type === 'error' ? 'fa-exclamation-circle text-red-400'
                       : 'fa-info-circle text-blue-400';
    toastIcon.className = `fas ${iconClass}`;

    toast.style.display = 'flex';

    setTimeout(() => {
        toast.style.display = 'none';
    }, 3000);
}

// ==================== AI CHATBOT FUNCTIONS ====================

// Chatbot state
let chatHistory = [];

// Initialize chatbot with welcome message
function initializeChatbot() {
    const messagesContainer = document.getElementById('chatbotMessages');
    messagesContainer.innerHTML = '';

    // Add welcome message
    addChatbotMessage("Hello! I'm your AI Investment Assistant. I can help you with:\n\n• Stock recommendations and suggestions\n• Portfolio analysis and optimization\n• Market insights and trends\n• Investment strategies\n• Diversification tips\n\nHow can I help you today?");
}

// Toggle chatbot window
function toggleChatbot() {
    const window = document.getElementById('chatbotWindow');
    window.classList.toggle('active');

    if (window.classList.contains('active') && chatHistory.length === 0) {
        initializeChatbot();
    }
}

// Send message from input
function sendChatbotMessage() {
    const input = document.getElementById('chatbotInput');
    const message = input.value.trim();

    if (message) {
        addUserMessage(message);
        processChatbotMessage(message);
        input.value = '';
    }
}

// Handle Enter key in chatbot input
function handleChatbotKeypress(event) {
    if (event.key === 'Enter') {
        sendChatbotMessage();
    }
}

// Send quick action message
function sendQuickMessage(message) {
    addUserMessage(message);
    processChatbotMessage(message);
}

// Add user message to chat
function addUserMessage(message) {
    const messagesContainer = document.getElementById('chatbotMessages');
    const messageDiv = document.createElement('div');
    messageDiv.className = 'message user';
    messageDiv.textContent = message;
    messagesContainer.appendChild(messageDiv);
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
}

// Add bot message to chat
function addChatbotMessage(message) {
    const messagesContainer = document.getElementById('chatbotMessages');
    const messageDiv = document.createElement('div');
    messageDiv.className = 'message bot';

    // Format message with basic markdown
    messageDiv.innerHTML = formatChatbotMessage(message);

    messagesContainer.appendChild(messageDiv);
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
}

// Format chatbot message with basic styling
function formatChatbotMessage(message) {
    // Convert newlines to <br>
    message = message.replace(/\n/g, '<br>');

    // Bold text
    message = message.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');

    // Stock symbols
    message = message.replace(/([A-Z]{2,5})/g, '<span style="background: #f1f5f9; padding: 2px 6px; border-radius: 4px; font-weight: 600;">$1</span>');

    // Stock recommendations with prices
    message = message.replace(/\$(\d+\.?\d*)/g, '<span style="color: #10b981; font-weight: 600;">$$$1</span>');

    // Percentage changes
    message = message.replace(/([+-]\d+\.?\d*%)/g, '<span style="color: #10b981; font-weight: 600;">$1</span>');

    return message;
}

// Process chatbot message and generate response
function processChatbotMessage(message) {
    const lowerMessage = message.toLowerCase();

    // Simulate AI thinking
    showTypingIndicator();

    setTimeout(() => {
        removeTypingIndicator();

        let response = '';

        // Stock suggestions
        if (lowerMessage.includes('suggest') || lowerMessage.includes('recommend') || lowerMessage.includes('good stocks') || lowerMessage.includes('invest in')) {
            response = generateStockSuggestion(lowerMessage);
        }
        // Portfolio analysis
        else if (lowerMessage.includes('analyze') || lowerMessage.includes('analysis') || lowerMessage.includes('portfolio')) {
            response = generatePortfolioAnalysis(lowerMessage);
        }
        // Top gainers
        else if (lowerMessage.includes('top gainers') || lowerMessage.includes('best performers') || lowerMessage.includes('gainers today')) {
            response = generateTopGainersInfo();
        }
        // Top losers
        else if (lowerMessage.includes('top losers') || lowerMessage.includes('worst performers') || lowerMessage.includes('losers today')) {
            response = generateTopLosersInfo();
        }
        // Diversification
        else if (lowerMessage.includes('diversif') || lowerMessage.includes('diversify') || lowerMessage.includes('spread risk')) {
            response = generateDiversificationTips();
        }
        // Market trends
        else if (lowerMessage.includes('market trend') || lowerMessage.includes('market outlook') || lowerMessage.includes('sector')) {
            response = generateMarketTrends();
        }
        // Buy/Sell recommendations
        else if (lowerMessage.includes('should i buy') || lowerMessage.includes('should i sell') || lowerMessage.includes('buy or sell')) {
            response = generateBuySellAdvice(lowerMessage);
        }
        // General help
        else if (lowerMessage.includes('help') || lowerMessage.includes('what can you do')) {
            response = generateHelpResponse();
        }
        // Default response
        else {
            response = generateDefaultResponse(lowerMessage);
        }

        addChatbotMessage(response);
    }, 1500);
}

// Show typing indicator
function showTypingIndicator() {
    const messagesContainer = document.getElementById('chatbotMessages');
    const typingDiv = document.createElement('div');
    typingDiv.id = 'typingIndicator';
    typingDiv.className = 'message bot';
    typingDiv.innerHTML = '<i class="fas fa-robot fa-fade"></i> Thinking...';
    messagesContainer.appendChild(typingDiv);
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
}

// Remove typing indicator
function removeTypingIndicator() {
    const typing = document.getElementById('typingIndicator');
    if (typing) {
        typing.remove();
    }
}

// Generate stock suggestions
function generateStockSuggestion(message) {
    const suggestions = [
        {
            stock: 'NVDA',
            reason: 'Strong AI/ML growth trend with excellent fundamentals',
            price: 875.30,
            potential: '+15-20%'
        },
        {
            stock: 'MSFT',
            reason: 'Solid cloud growth and AI integration',
            price: 420.10,
            potential: '+10-12%'
        },
        {
            stock: 'META',
            reason: 'Recovery in digital advertising and VR growth',
            price: 485.23,
            potential: '+12-15%'
        },
        {
            stock: 'AMD',
            reason: 'Gaining market share in CPUs and GPUs',
            price: 180.25,
            potential: '+18-22%'
        }
    ];

    let response = 'Based on current market analysis and trends, here are my top stock recommendations:\n\n';

    suggestions.forEach(s => {
        response += `**${s.stock}** - $${s.price.toFixed(2)}\n`;
        response += `💡 Reason: ${s.reason}\n`;
        response += `📈 Potential: ${s.potential}\n\n`;
    });

    response += '*Note: These are AI-generated suggestions. Always do your own research before investing.*';

    return response;
}

// Generate portfolio analysis
function generatePortfolioAnalysis(message) {
    const summary = calculatePortfolioSummary(demoPortfolioData);

    let response = '**Portfolio Analysis Summary:**\n\n';
    response += `📊 Total Value: ${formatCurrency(summary.totalValue)}\n`;
    response += `💰 Total Investment: ${formatCurrency(summary.totalInvestment)}\n`;
    response += `📈 Overall Return: ${summary.gainLossPercent >= 0 ? '+' : ''}${summary.gainLossPercent.toFixed(2)}%\n`;
    response += `💵 Absolute Gain/Loss: ${summary.totalGainLoss >= 0 ? '+' : ''}${formatCurrency(summary.totalGainLoss)}\n\n`;

    if (summary.gainLossPercent > 15) {
        response += '✅ **Excellent Performance!** Your portfolio is performing above market average.\n\n';
    } else if (summary.gainLossPercent > 5) {
        response += '👍 **Good Performance** - Your portfolio is growing steadily.\n\n';
    } else if (summary.gainLossPercent > 0) {
        response += '📊 **Moderate Growth** - Consider diversifying for better returns.\n\n';
    } else {
        response += '⚠️ **Underperforming** - Review your holdings and consider rebalancing.\n\n';
    }

    response += '**Recommendations:**\n';
    response += '• Review holdings with negative returns\n';
    response += '• Consider adding defensive stocks\n';
    response += '• Rebalance portfolio quarterly';

    return response;
}

// Generate top gainers info
function generateTopGainersInfo() {
    let response = '**🔥 Top Gainers Today:**\n\n';

    topGainersData.slice(0, 4).forEach((stock, i) => {
        response += `${i + 1}. **${stock.symbol}** - ${stock.name}\n`;
        response += `   Price: $${stock.price.toFixed(2)} | Change: +${stock.changePercent.toFixed(2)}%\n`;
        response += `   Volume: ${stock.volume}\n\n`;
    });

    response += 'These stocks are showing strong momentum. Consider them for short-term trades.';

    return response;
}

// Generate top losers info
function generateTopLosersInfo() {
    let response = '**📉 Top Losers Today:**\n\n';

    topLosersData.slice(0, 4).forEach((stock, i) => {
        response += `${i + 1}. **${stock.symbol}** - ${stock.name}\n`;
        response += `   Price: $${stock.price.toFixed(2)} | Change: ${stock.changePercent.toFixed(2)}%\n`;
        response += `   Volume: ${stock.volume}\n\n`;
    });

    response += 'Monitor these stocks for potential buying opportunities if fundamentals are strong.';

    return response;
}

// Generate diversification tips
function generateDiversificationTips() {
    let response = '**🎯 Portfolio Diversification Strategies:**\n\n';

    response += '**1. Asset Allocation**\n';
    response += '• Stocks: 60-70%\n';
    response += '• Bonds: 20-30%\n';
    response += '• Alternatives: 5-10%\n\n';

    response += '**2. Sector Diversification**\n';
    response += '• Technology: 20-25%\n';
    response += '• Healthcare: 15-20%\n';
    response += '• Financial: 15-20%\n';
    response += '• Consumer: 10-15%\n';
    response += '• Energy: 5-10%\n\n';

    response += '**3. Geographic Diversification**\n';
    response += '• Domestic: 60-70%\n';
    response += '• International: 20-30%\n';
    response += '• Emerging Markets: 5-10%\n\n';

    response += '**Key Principle:** Don\'t put all eggs in one basket!';

    return response;
}

// Generate market trends
function generateMarketTrends() {
    let response = '**📈 Market Trends & Sector Performance:**\n\n';

    sectorsTrendingData.forEach(sector => {
        const icon = sector.change >= 0 ? '📗' : '📕';
        const sign = sector.change >= 0 ? '+' : '';
        response += `${icon} **${sector.name}**: ${sign}${sector.change.toFixed(2)}%\n`;
    });

    response += '\n**Current Market Sentiment:**\n';
    response += '• Technology sector leading with AI/ML adoption\n';
    response += '• Healthcare showing steady growth\n';
    response += '• Energy volatile due to geopolitical factors\n';
    response += '• Overall market outlook: BULLISH\n\n';
    response += 'Consider overweighting Technology and Healthcare in current conditions.';

    return response;
}

// Generate buy/sell advice
function generateBuySellAdvice(message) {
    // Extract stock symbol if mentioned
    const stockMatch = message.match(/([A-Z]{2,5})/);
    const symbol = stockMatch ? stockMatch[1] : null;

    if (symbol) {
        const marketStock = demoMarketData.find(s => s.symbol === symbol);
        const portfolioStock = demoPortfolioData.find(s => s.tickerSymbol === symbol);

        let response = `**${symbol} Analysis:**\n\n`;

        if (marketStock) {
            const trend = marketStock.changePercent >= 0 ? 'upward' : 'downward';
            response += `Current Price: $${marketStock.price.toFixed(2)}\n`;
            response += `Today's Change: ${marketStock.changePercent >= 0 ? '+' : ''}${marketStock.changePercent.toFixed(2)}%\n\n`;
            response += `The stock is showing **${trend}** momentum.\n\n`;
        }

        response += '**My Assessment:**\n';
        response += '• Fundamentals: Strong\n';
        response += '• Technicals: Bullish\n';
        response += '• Overall: CONSIDER BUYING on dips\n\n';
        response += '*Disclaimer: This is AI-generated advice. Always consult a financial advisor.*';
    } else {
        response = 'To provide specific buy/sell advice, please mention a stock symbol (e.g., "Should I buy AAPL?").\n\n';
        response += 'I analyze factors like:\n';
        response += '• Price trends and momentum\n';
        response += '• Trading volume patterns\n';
        response += '• Market sentiment\n';
        response += '• Portfolio correlation';
    }

    return response;
}

// Generate help response
function generateHelpResponse() {
    return `**🤖 AI Investment Assistant - Help Guide:**\n\n` +
           `I can help you with:\n\n` +
           `📊 **Stock Analysis**\n` +
           `   "Analyze AAPL"\n` +
           `   "Should I buy NVDA?"\n\n` +
           `💡 **Recommendations**\n` +
           `   "Suggest good stocks"\n` +
           `   "What should I invest in?"\n\n` +
           `📈 **Market Insights**\n` +
           `   "Top gainers today"\n` +
           `   "Market trends"\n` +
           `   "Sector performance"\n\n` +
           `🎯 **Portfolio Tips**\n` +
           `   "Analyze my portfolio"\n` +
           `   "How to diversify"\n\n` +
           `Just ask me anything about investing!`;
}

// Generate default response
function generateDefaultResponse(message) {
    const responses = [
        `I understand you're asking about "${message.substring(0, 50)}..."\n\nCould you provide more details so I can help you better?`,
        `That's an interesting question about "${message.substring(0, 30)}..."\n\nI can assist with stock analysis, portfolio tips, and market insights. What specific information do you need?`,
        `I'm here to help with your investment questions!\n\nTry asking about:\n• Stock recommendations\n• Portfolio analysis\n• Market trends\n• Diversification strategies`
    ];

    return responses[Math.floor(Math.random() * responses.length)];
}

// Make functions globally available
window.showSection = showSection;
window.editPosition = editPosition;
window.deletePosition = deletePosition;
window.quickTrade = quickTrade;
window.showAddModal = showAddModal;
window.closeModal = closeModal;
window.refreshMarketData = refreshMarketData;
window.refreshAllData = refreshAllData;
window.refreshSuggestions = refreshSuggestions;
window.refreshNews = refreshNews;
window.addToWatchlist = addToWatchlist;
window.showNotifications = showNotifications;
window.toggleSidebar = toggleSidebar;
window.updateChartPeriod = updateChartPeriod;
window.handleFormSubmit = handleFormSubmit;
window.scrollToSection = scrollToSection;

// Chatbot functions
window.toggleChatbot = toggleChatbot;
window.sendChatbotMessage = sendChatbotMessage;
window.handleChatbotKeypress = handleChatbotKeypress;
window.sendQuickMessage = sendQuickMessage;
