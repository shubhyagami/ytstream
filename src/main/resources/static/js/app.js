/* ═══════════════════════════════════════════════════════════
   VidStream App JS
   ═══════════════════════════════════════════════════════════ */

const API = '/api';

// ── State ──────────────────────────────────────────────────
const state = {
  source:      'youtube',
  query:       '',
  results:     [],
  queue:       [],
  queueIndex:  -1,
  currentToken: null,
  isPlaying:   false,
  isLoading:   false,
  isStreaming: false,
  shuffle:     false,
  repeat:      false,  // false | 'one' | 'all'
  volume:      0.8,
};

// ── DOM Refs ───────────────────────────────────────────────
const $ = id => document.getElementById(id);
const els = {
  searchInput:    $('search-input'),
  searchBtn:      $('search-btn'),
  sourceTabs:     document.querySelectorAll('.source-tab'),
  content:        $('content'),
  playerBar:      $('player-bar'),
  nowThumb:       $('now-thumb'),
  nowTitle:       $('now-title'),
  nowArtist:      $('now-artist'),
  nowLike:        $('now-like'),
  ctrlPrev:       $('ctrl-prev'),
  ctrlPlay:       $('ctrl-play'),
  ctrlNext:       $('ctrl-next'),
  ctrlShuffle:    $('ctrl-shuffle'),
  ctrlRepeat:     $('ctrl-repeat'),
  seekBar:        $('seek-bar'),
  seekFill:       $('seek-fill'),
  seekThumb:      $('seek-thumb'),
  timeEl:         $('time-current'),
  durEl:          $('time-total'),
  volSlider:      $('vol-slider'),
  volFill:        $('vol-fill'),
  volIcon:        $('vol-icon'),
  queueList:      $('queue-list'),
  queueCount:     $('queue-count'),
  toastContainer: $('toast-container'),
  streamOverlay:  $('stream-overlay'),
  overlayThumb:   $('overlay-thumb'),
  overlayTitle:   $('overlay-title'),
  overlaySub:     $('overlay-sub'),
  videoModal:     $('video-modal'),
  videoPlayer:    $('video-player'),
  videoClose:     $('video-close'),
};

// Native audio element for audio-only tracks
const audioEl = new Audio();
audioEl.preload = 'auto';

// ── Init ───────────────────────────────────────────────────
function init() {
  audioEl.volume = state.volume;
  updateVolUI();
  bindEvents();
  showEmpty('🎵', 'Search for anything', 'YouTube • Spotify • SoundCloud — all in one place');

  // Restore queue from sessionStorage
  try {
    const saved = sessionStorage.getItem('vs_queue');
    if (saved) {
      const restored = JSON.parse(saved);
      state.queue = restored.items || [];
      state.queueIndex = restored.index != null ? restored.index : -1;
      renderQueue();
    }
  } catch (_) {}
}

// ── Event Binding ──────────────────────────────────────────
function bindEvents() {
  els.searchBtn.addEventListener('click', triggerSearch);
  els.searchInput.addEventListener('keydown', e => { if (e.key === 'Enter') triggerSearch(); });

  els.sourceTabs.forEach(tab => {
    tab.addEventListener('click', () => {
      state.source = tab.dataset.source;
      els.sourceTabs.forEach(t => t.classList.remove('active'));
      tab.classList.add('active');
    });
  });

  // Player controls
  els.ctrlPlay.addEventListener('click', togglePlay);
  els.ctrlPrev.addEventListener('click', playPrev);
  els.ctrlNext.addEventListener('click', playNext);
  els.ctrlShuffle.addEventListener('click', toggleShuffle);
  els.ctrlRepeat.addEventListener('click', toggleRepeat);
  els.nowLike.addEventListener('click', toggleLike);

  // Seek bar
  els.seekBar.addEventListener('click', onSeekClick);

  // Volume
  els.volSlider.addEventListener('click', onVolClick);
  els.volIcon.addEventListener('click', toggleMute);

  // Audio events
  audioEl.addEventListener('timeupdate', onTimeUpdate);
  audioEl.addEventListener('ended', onTrackEnded);
  audioEl.addEventListener('loadedmetadata', onMeta);
  audioEl.addEventListener('error', e => showToast('Playback error', 'error'));

  // Video modal
  els.videoClose.addEventListener('click', closeVideoModal);
  els.videoModal.addEventListener('click', e => { if (e.target === els.videoModal) closeVideoModal(); });
  els.videoPlayer.addEventListener('ended', onTrackEnded);

  // Keyboard shortcuts
  document.addEventListener('keydown', onKeyDown);
}

// ── Search ─────────────────────────────────────────────────
async function triggerSearch() {
  const q = els.searchInput.value.trim();
  if (!q) return;
  state.query = q;
  showLoading();

  try {
    const res = await fetch(`${API}/search?q=${encodeURIComponent(q)}&source=${state.source}&limit=24`);
    const data = await res.json();
    if (!res.ok) throw new Error(data.error || 'Search failed');
    state.results = data.results || [];
    renderResults();
  } catch (e) {
    showToast('Search failed: ' + e.message, 'error');
    showEmpty('⚠️', 'Search failed', e.message);
  }
}

// ── Render ─────────────────────────────────────────────────
function renderResults() {
  if (state.results.length === 0) {
    showEmpty('🔍', 'No results found', `Try a different query or source`);
    return;
  }

  const header = `
    <div class="results-header">
      <span class="results-title">Results for "${escHtml(state.query)}"</span>
      <span class="results-count">${state.results.length} found</span>
    </div>`;

  const cards = state.results.map((r, i) => mediaCard(r, i)).join('');
  els.content.innerHTML = header + `<div class="results-grid">${cards}</div>`;

  // Bind card events
  document.querySelectorAll('.media-card').forEach((card, i) => {
    card.addEventListener('click', e => {
      if (e.target.closest('.card-add-queue')) {
        addToQueue(state.results[i]);
        return;
      }
      playNow(state.results[i]);
    });
  });
}

function mediaCard(r, i) {
  const thumb = r.thumbnail ? r.thumbnail : '';
  const dur   = r.durationSeconds > 0 ? fmtTime(r.durationSeconds) : '';
  const src   = r.source;
  const artist = escHtml(r.artist || r.channel || '');

  return `<div class="media-card" data-i="${i}">
    <div class="card-thumb-wrap">
      ${thumb
        ? `<img class="card-thumb" src="${escHtml(thumb)}" alt="" loading="lazy" onerror="this.style.display='none'">`
        : `<div class="card-thumb" style="background:linear-gradient(135deg,#1e1e2e,#2a1e3a);display:flex;align-items:center;justify-content:center;font-size:40px;aspect-ratio:16/9">${src === 'spotify' ? '🎵' : src === 'soundcloud' ? '🔊' : '▶️'}</div>`
      }
      <div class="card-overlay">
        <div class="play-btn-overlay">▶</div>
      </div>
      <button class="card-add-queue" title="Add to queue">+</button>
      ${dur ? `<span class="card-badge">${dur}</span>` : ''}
      <span class="source-badge ${src}">${src}</span>
    </div>
    <div class="card-info">
      <div class="card-title">${escHtml(r.title)}</div>
      ${artist ? `<div class="card-artist">${artist}</div>` : ''}
    </div>
  </div>`;
}

function showLoading() {
  const skels = Array.from({length: 12}, () => `
    <div class="skeleton">
      <div class="skeleton-thumb"></div>
      <div class="skeleton-info">
        <div class="skeleton-line"></div>
        <div class="skeleton-line short"></div>
      </div>
    </div>`).join('');
  els.content.innerHTML = `<div class="loading-grid">${skels}</div>`;
}

function showEmpty(icon, title, sub) {
  els.content.innerHTML = `
    <div class="state-empty">
      <div class="state-icon">${icon}</div>
      <div class="state-title">${title}</div>
      <div class="state-sub">${sub}</div>
    </div>`;
}

// ── Queue ──────────────────────────────────────────────────
function addToQueue(item) {
  if (state.queue.some(q => q.id === item.id)) {
    showToast('Already in queue', 'info'); return;
  }
  state.queue.push(item);
  saveQueue();
  renderQueue();
  showToast(`Added: ${item.title.substring(0, 30)}`, 'success');
}

function removeFromQueue(idx) {
  state.queue.splice(idx, 1);
  if (state.queueIndex >= idx) state.queueIndex = Math.max(0, state.queueIndex - 1);
  saveQueue();
  renderQueue();
}

function renderQueue() {
  els.queueCount.textContent = state.queue.length ? `(${state.queue.length})` : '';
  if (state.queue.length === 0) {
    els.queueList.innerHTML = '<div style="padding:12px 8px;font-size:12px;color:var(--text-3);text-align:center">Queue is empty</div>';
    return;
  }
  els.queueList.innerHTML = state.queue.map((item, i) => `
    <div class="queue-item ${i === state.queueIndex ? 'playing' : ''}" data-qi="${i}">
      ${item.thumbnail
        ? `<img class="queue-thumb" src="${escHtml(item.thumbnail)}" alt="" onerror="this.style.display='none'">`
        : `<div class="queue-thumb" style="background:var(--bg-card2);display:flex;align-items:center;justify-content:center">🎵</div>`}
      <div class="queue-info">
        <div class="queue-title">${escHtml(item.title)}</div>
        <div class="queue-artist">${escHtml(item.artist || item.channel || '')}</div>
      </div>
      <button class="queue-remove" onclick="removeFromQueue(${i});event.stopPropagation()">✕</button>
    </div>`).join('');

  document.querySelectorAll('.queue-item').forEach((el, i) => {
    el.addEventListener('click', () => playQueueItem(i));
  });
}

function saveQueue() {
  try { sessionStorage.setItem('vs_queue', JSON.stringify({ items: state.queue, index: state.queueIndex })); } catch (_) {}
}

function playQueueItem(idx) {
  state.queueIndex = idx;
  playNow(state.queue[idx], true);
}

// ── Playback ───────────────────────────────────────────────
async function playNow(item, fromQueue = false) {
  if (!fromQueue) {
    if (!state.queue.find(q => q.id === item.id)) {
      state.queue.push(item);
    }
    state.queueIndex = state.queue.findIndex(q => q.id === item.id);
    saveQueue();
    renderQueue();
  }

  // Stop current
  stopCurrent();

  showStreamOverlay(item);
  state.isStreaming = true;

  try {
    const res = await fetch(`${API}/stream`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ id: item.id, source: item.source, title: item.title })
    });
    const data = await res.json();
    if (!res.ok) throw new Error(data.error || 'Stream request failed');

    state.currentToken = data.token;
    const streamUrl = `${API}/stream/${data.token}`;

    hideStreamOverlay();
    updateNowPlaying(item);

    // Use the actual media type from backend (audio/video)
    const isVideo = data.mediaType === 'video';

    if (isVideo) {
      openVideoModal(streamUrl, item);
    } else {
      // Audio playback via bottom player bar
      audioEl.src = streamUrl;
      audioEl.play().catch(e => showToast('Playback error: ' + e.message, 'error'));
      state.isPlaying = true;
      updatePlayBtn();
    }

  } catch (e) {
    hideStreamOverlay();
    showToast('Streaming failed: ' + e.message, 'error');
    state.isStreaming = false;
  }
}

function stopCurrent() {
  audioEl.pause();
  audioEl.src = '';
  if (els.videoPlayer.src) {
    els.videoPlayer.pause();
    els.videoPlayer.src = '';
  }
  if (state.currentToken) {
    fetch(`${API}/stream/${state.currentToken}`, { method: 'DELETE' }).catch(() => {});
    state.currentToken = null;
  }
  state.isPlaying = false;
  updatePlayBtn();
}

function togglePlay() {
  if (!state.currentToken) return;
  const isVideo = els.videoModal.classList.contains('show');
  const media   = isVideo ? els.videoPlayer : audioEl;
  if (state.isPlaying) {
    media.pause(); state.isPlaying = false;
  } else {
    media.play(); state.isPlaying = true;
  }
  updatePlayBtn();
}

function playPrev() {
  if (state.queue.length === 0) return;
  let idx = state.queueIndex - 1;
  if (idx < 0) idx = state.queue.length - 1;
  playQueueItem(idx);
}

function playNext() {
  if (state.queue.length === 0) return;
  let idx;
  if (state.shuffle) {
    idx = Math.floor(Math.random() * state.queue.length);
  } else {
    idx = state.queueIndex + 1;
    if (idx >= state.queue.length) {
      if (state.repeat === 'all') idx = 0;
      else { stopCurrent(); return; }
    }
  }
  playQueueItem(idx);
}

function onTrackEnded() {
  if (state.repeat === 'one') {
    const media = els.videoModal.classList.contains('show') ? els.videoPlayer : audioEl;
    media.currentTime = 0;
    media.play();
  } else {
    const hasNext = state.shuffle || state.queueIndex + 1 < state.queue.length;
    playNext();
    // If queue is done and not repeating, clean up the file
    if (!hasNext && state.repeat !== 'all') {
      cleanupToken();
    }
  }
}

function cleanupToken() {
  if (state.currentToken) {
    fetch(`${API}/stream/${state.currentToken}`, { method: 'DELETE' }).catch(() => {});
    state.currentToken = null;
  }
}

function toggleShuffle() {
  state.shuffle = !state.shuffle;
  els.ctrlShuffle.classList.toggle('active', state.shuffle);
}

function toggleRepeat() {
  const modes = [false, 'one', 'all'];
  const idx   = modes.indexOf(state.repeat);
  state.repeat = modes[(idx + 1) % modes.length];
  els.ctrlRepeat.classList.toggle('active', !!state.repeat);
  els.ctrlRepeat.textContent = state.repeat === 'one' ? '🔂' : '🔁';
}

function toggleLike() {
  els.nowLike.classList.toggle('liked');
}

// ── Now Playing UI ─────────────────────────────────────────
function updateNowPlaying(item) {
  els.nowThumb.src = item.thumbnail || '';
  els.nowThumb.style.display = item.thumbnail ? '' : 'none';
  els.nowTitle.textContent  = item.title;
  els.nowArtist.textContent = item.artist || item.channel || '';
  document.title = `▶ ${item.title} — VidStream`;
}

function updatePlayBtn() {
  els.ctrlPlay.textContent = state.isPlaying ? '⏸' : '▶';
}

// ── Seek & Time ────────────────────────────────────────────
function onTimeUpdate() {
  const media = els.videoModal.classList.contains('show') ? els.videoPlayer : audioEl;
  if (!isFinite(media.duration)) return;
  const pct = (media.currentTime / media.duration) * 100;
  els.seekFill.style.width  = pct + '%';
  els.seekThumb.style.left  = pct + '%';
  els.playerBar.style.setProperty('--progress', pct + '%');
  els.timeEl.textContent = fmtTime(media.currentTime);
}

function onMeta() {
  const media = els.videoModal.classList.contains('show') ? els.videoPlayer : audioEl;
  els.durEl.textContent = fmtTime(media.duration);
}

function onSeekClick(e) {
  const media = els.videoModal.classList.contains('show') ? els.videoPlayer : audioEl;
  if (!isFinite(media.duration)) return;
  const rect = els.seekBar.getBoundingClientRect();
  const pct  = Math.max(0, Math.min(1, (e.clientX - rect.left) / rect.width));
  media.currentTime = pct * media.duration;
}

// ── Volume ─────────────────────────────────────────────────
function onVolClick(e) {
  const rect = els.volSlider.getBoundingClientRect();
  state.volume = Math.max(0, Math.min(1, (e.clientX - rect.left) / rect.width));
  audioEl.volume = state.volume;
  if (els.videoPlayer.src) els.videoPlayer.volume = state.volume;
  updateVolUI();
}

function toggleMute() {
  if (audioEl.volume > 0) {
    state._prevVol = state.volume;
    state.volume = 0; audioEl.volume = 0;
  } else {
    state.volume = state._prevVol || 0.8;
    audioEl.volume = state.volume;
  }
  updateVolUI();
}

function updateVolUI() {
  els.volFill.style.width = (state.volume * 100) + '%';
  els.volIcon.textContent = state.volume === 0 ? '🔇' : state.volume < 0.5 ? '🔉' : '🔊';
}

// ── Video Modal ────────────────────────────────────────────
function openVideoModal(url, item) {
  els.videoPlayer.src = url;
  els.videoPlayer.volume = state.volume;
  els.videoModal.classList.add('show');
  els.videoPlayer.play();
  state.isPlaying = true;
  updatePlayBtn();

  els.videoPlayer.addEventListener('timeupdate', onTimeUpdate);
  els.videoPlayer.addEventListener('loadedmetadata', onMeta);
}

function closeVideoModal() {
  els.videoPlayer.pause();
  els.videoPlayer.src = '';
  els.videoModal.classList.remove('show');
  state.isPlaying = false;
  updatePlayBtn();
  document.title = 'VidStream';
}

// ── Stream Overlay ─────────────────────────────────────────
function showStreamOverlay(item) {
  els.overlayThumb.src   = item.thumbnail || '';
  els.overlayThumb.style.display = item.thumbnail ? '' : 'none';
  els.overlayTitle.textContent   = item.title.substring(0, 50);
  els.overlaySub.textContent     = 'Preparing stream via yt-dlp…';
  els.streamOverlay.classList.add('show');
}

function hideStreamOverlay() {
  els.streamOverlay.classList.remove('show');
  state.isStreaming = false;
}

// ── Toast ──────────────────────────────────────────────────
function showToast(msg, type = 'info', duration = 3500) {
  const icon = type === 'success' ? '✅' : type === 'error' ? '❌' : 'ℹ️';
  const el = document.createElement('div');
  el.className = `toast ${type}`;
  el.innerHTML = `<span>${icon}</span><span>${escHtml(msg)}</span>`;
  els.toastContainer.appendChild(el);
  setTimeout(() => el.remove(), duration);
}

// ── Keyboard Shortcuts ─────────────────────────────────────
function onKeyDown(e) {
  if (e.target.tagName === 'INPUT') return;
  if (e.code === 'Space') { e.preventDefault(); togglePlay(); }
  if (e.code === 'ArrowRight') { const m = currentMedia(); if (m) m.currentTime += 10; }
  if (e.code === 'ArrowLeft')  { const m = currentMedia(); if (m) m.currentTime -= 10; }
  if (e.code === 'ArrowUp')    { state.volume = Math.min(1, state.volume + 0.1); audioEl.volume = state.volume; updateVolUI(); }
  if (e.code === 'ArrowDown')  { state.volume = Math.max(0, state.volume - 0.1); audioEl.volume = state.volume; updateVolUI(); }
  if (e.code === 'Escape') closeVideoModal();
}

function currentMedia() {
  return els.videoModal.classList.contains('show') ? els.videoPlayer : audioEl;
}

// ── Utilities ──────────────────────────────────────────────
function fmtTime(s) {
  if (!isFinite(s) || s < 0) return '0:00';
  const h = Math.floor(s / 3600), m = Math.floor((s % 3600) / 60), ss = Math.floor(s % 60);
  return h > 0
    ? `${h}:${String(m).padStart(2,'0')}:${String(ss).padStart(2,'0')}`
    : `${m}:${String(ss).padStart(2,'0')}`;
}

function escHtml(s) {
  if (!s) return '';
  return String(s)
    .replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;').replace(/'/g, '&#x27;');
}

// ── Boot ───────────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', init);
